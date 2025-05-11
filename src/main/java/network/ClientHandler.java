package network;


import EntityDTO.ItemDTO;
import EntityDTO.OrderItemInfoDTO;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import entity.Buyer;
import entity.Role;
import entity.User;
import EntityDTO.UserDTO;
import Enums.ResponseStatus;
import Services.UserService;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Services.*;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private Request request;
    private Response response;
    private Gson gson;
    private BufferedReader in;
    private PrintWriter out;


    private UserService userService = new UserService();
    private RoleService roleService = new RoleService();
    private OrderService orderService = new OrderService();
    private Order_ItemService orderItemsService = new Order_ItemService();
    private ItemService itemService = new ItemService();
    private BuyerService buyerService = new BuyerService();


    public ClientHandler(Socket clientSocket) throws IOException
    {
        response = new Response();
        request = new Request();
        this.clientSocket = clientSocket;
        gson = new Gson();
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new PrintWriter(clientSocket.getOutputStream());
    }

    @Override
    public void run()
    {
        try {
            while (clientSocket.isConnected()) {
                String message = in.readLine();

                request = gson.fromJson(message, Request.class);
                switch (request.getRequestType()) {
                    case REGISTER: {
                        UserDTO requestUser = gson.fromJson(request.getData(), UserDTO.class);
                        User user = new User();
                        user.setUserName(requestUser.getUserName());
                        user.setPassword(requestUser.getPassword());

                        Buyer buyer = new Buyer();
                        buyer.setBuyerAddress(requestUser.getBuyerDTO().getBuyerAddress());
                        buyer.setBuyerPhone(requestUser.getBuyerDTO().getBuyerPhone());

                        Role role = roleService.findEntity(2); // id 2 — это ROLE_USER
                        user.setRole(role);

                        buyer.setUser(user);      // Важно для связи @OneToOne
                        user.setBuyer(buyer);     // Важно для cascade = ALL

                        if (userService.register(user)) {
                            response = new Response(ResponseStatus.OK, "Готово!", "");
                        } else {
                            response = new Response(ResponseStatus.ERROR, "Имя пользователя занято!", "");
                        }
                        break;
                    }
                    case LOGIN: {
                        UserDTO requestUser = gson.fromJson(request.getData(), UserDTO.class);
                        User user = new User();
                        user.setUserName(requestUser.getUserName());
                        user.setPassword(requestUser.getPassword());
                        requestUser = userService.login(user);

                        if (requestUser != null) {
                            response = new Response(ResponseStatus.OK, "Готово!", gson.toJson(requestUser));
                        } else {
                            response = new Response(ResponseStatus.ERROR, "Такого пользователя не существует или неправильный пароль!", "");
                        }
                        break;
                    }
                    case GET_ALL_ITEMS: {
                        List<ItemDTO> items;
                        items = itemService.findAll();
                        if (items != null) {
                            response = new Response(ResponseStatus.OK, "Готово!", gson.toJson(items));
                        } else {
                            response = new Response(ResponseStatus.ERROR, "Ошибка запроса данных!", "");
                        }
                        break;
                    }
                    case MAKE_ORDER:
                    {
                        Type type = new TypeToken<HashMap<Integer, ArrayList<ItemDTO>>>() {}.getType();
                        Map<Integer, ArrayList<ItemDTO>> order = new Gson().fromJson(request.getData(), type);
                        int check = orderService.createOrder(order);
                        if (check == -1) {
                            response = new Response(ResponseStatus.ERROR, "На стороне сервера произошла ошибка!", "");
                        } else if (check == 0){
                            response = new Response(ResponseStatus.OK, "Были куплены не все товары. Попробуйте позже.", "");
                        }
                        else
                            response = new Response(ResponseStatus.OK, "Заказ был успешно оформлен.", "");

                        break;
                    }
                    case GET_ALL_ORDERS_BY_ID:
                        int id = gson.fromJson(request.getData(), Integer.class);
                        List<OrderItemInfoDTO> userOrders= orderService.findEntityByUserId(id);
                        Gson gson1 = new GsonBuilder()
                                .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>)
                                        (src, typeOfSrc, context) -> new JsonPrimitive(src.toString()))
                                .create();
                        if(userOrders != null)
                        {
                            response = new Response(ResponseStatus.OK, "", gson1.toJson(userOrders));
                        }
                        else
                        {
                            response = new Response(ResponseStatus.ERROR, "Заказы пользователя отсутствуют!", "");
                        }
                        break;
                }
                out.println(gson.toJson(response));
                out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("Клиент " + clientSocket.getInetAddress() + ":" + clientSocket.getPort() + " закрыл соединение.");
            try {

                clientSocket.close();
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
