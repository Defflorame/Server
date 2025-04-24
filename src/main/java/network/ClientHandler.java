package network;


import entity.User;
import EntityDTO.UserDTO;
import Enums.ResponseStatus;
import Services.UserService;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

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


    public ClientHandler(Socket clientSocket) throws IOException {
        response = new Response();
        request = new Request();
        this.clientSocket = clientSocket;
        gson = new Gson();
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new PrintWriter(clientSocket.getOutputStream());
    }

    @Override
    public void run() {
        try {
            while (clientSocket.isConnected()) {
                String message = in.readLine();

                request = gson.fromJson(message, Request.class);
                switch (request.getRequestType()) {
                    case REGISTER:
                    {
//                        User user = gson.fromJson(request.getData(), User.class);
//                        if (userService.findAllEntities().stream().noneMatch(x -> x.getLogin().toLowerCase().equals(user.getLogin().toLowerCase()))) {
//                            personDataService.saveEntity(user.getPersonData());
//                            userService.saveEntity(user);
//                            userService.findAllEntities();
//                            User returnUser;
//                            returnUser = userService.findEntity(user.getId());
//                            returnUser.setUserMarks(null);
//                            response = new Response(ResponseStatus.OK, "Готово!", gson.toJson(returnUser));
//                        } else {
//                            response = new Response(ResponseStatus.ERROR, "Такой пользователь уже существует!", "");
//                        }
                        break;
                    }
                    case LOGIN: {
                        UserDTO requestUser = gson.fromJson(request.getData(), UserDTO.class);
                        if (requestUser != null) {
                            System.out.println("Пользователь найден: " + requestUser.getUserName());
                            response = new Response(ResponseStatus.OK, "Готово!", gson.toJson(requestUser));
                        } else {
                            System.out.println("Пользователь не найден.");
                            response = new Response(ResponseStatus.ERROR, "Такого пользователя не существует или неправильный пароль!", "");
                        }
                        User user = new User();
                        user.setUserName(requestUser.getUserName());
                        user.setPassword(requestUser.getPassword());
                        requestUser = userService.login(user);
                        System.out.println("Ответ на запрос: " + gson.toJson(response));
                        out.println(gson.toJson(response));
                        out.flush();
                        if (requestUser != null) {
                            response = new Response(ResponseStatus.OK, "Готово!", gson.toJson(requestUser));
                        }
                        else
                        {
                            response = new Response(ResponseStatus.ERROR, "Такого пользователя не существует или неправильный пароль!", "");
                        }
                        break;
                    }
//                    case ADD_FLIGHT:
//                        Flight flight = gson.fromJson(request.getData(), Flight.class);
//                        routeService.saveEntity(flight.getRoute());
//                        aircraftService.saveEntity(flight.getAircraft());
//                        flightService.saveEntity(flight);
//                        response = new Response(ResponseStatus.OK, "Готово!", "");
//                        break;
//                    case DELETE_FLIGHT:
//                        flight = gson.fromJson(request.getData(), Flight.class);
//                        flightService.deleteEntity(flight);
//                        response = new Response(ResponseStatus.OK, "Готово!", "");
//                        break;
//                    case GET_FLIGHT:
//                        flight = gson.fromJson(request.getData(), Flight.class);
//                        flight = flightService.findEntity(flight.getId());
//                        response = new Response(ResponseStatus.OK, "Готово!", gson.toJson(flight));
//                        break;
//                    case GETALL_FLIGHT:
//                        List<Flight> flights = new ArrayList<>();
//                        List<ResultMark> result = calcCondorcet();
//                        for (ResultMark resultMark :
//                                result) {
//                            flights.add(resultMark.getFlight());
//                        }
//                        response = new Response(ResponseStatus.OK, "Готово!", gson.toJson(flights));
//                        break;
//                    case UPDATE_FLIGHT:
//                        flight = gson.fromJson(request.getData(), Flight.class);
//                        routeService.updateEntity(flight.getRoute());
//                        aircraftService.updateEntity(flight.getAircraft());
//                        flightService.updateEntity(flight);
//                        response = new Response(ResponseStatus.OK, "Готово!", "");
//                        break;
//                    case UPDATE_MARK:
//                        UserMark mark = gson.fromJson(request.getData(), UserMark.class);
//                        userMarkService.updateEntity(mark);
//                        response = new Response(ResponseStatus.OK, "Готово!", "");
//                        break;
//                    case UPDATE_PASSENGER:
//                        Passenger passenger = gson.fromJson(request.getData(), Passenger.class);
//                        List<Passenger> passengers = passengerService.findAllEntities();
//                        if (passengers.stream().anyMatch(x -> x.getPlaceNumber() == passenger.getPlaceNumber() && x.getId() != passenger.getId() && x.getFlight().getId()== passenger.getFlight().getId())) {
//                            response = new Response(ResponseStatus.ERROR, "Это место уже занято!", "");
//                            break;
//                        }
//                        passengerService.updateEntity(passenger);
//                        response = new Response(ResponseStatus.OK, "Готово!", "");
//                        break;
//                    case CONDORCET:
//                        result = calcCondorcet();
//                        response = new Response(ResponseStatus.OK, "Готово!", gson.toJson(result));
//                        break;
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
