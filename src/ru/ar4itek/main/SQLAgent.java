package ru.ar4itek.main;

    import java.sql.Connection;
    import java.sql.SQLException;
    import java.sql.Statement;
    import java.sql.ResultSet;
    import java.sql.DriverManager;

public class SQLAgent implements ru.ar4itek.interfaces.connect, ru.ar4itek.interfaces.closeConnection, ru.ar4itek.interfaces.setData, ru.ar4itek.interfaces.executeTask, ru.ar4itek.interfaces.setDebugmode {

        String user = null;
        String password = null;
        String sqltype = null;
        String url = null;
        Boolean debugmode = false;
        int port = 0;
        Connection connection;
        Statement statement;

    public SQLAgent(String user, String url, String password, String sqltype, int port){

        this.user = user;
        this.password = password;
        this.port = port;
        this.sqltype = sqltype;
        this.url = url;

    }

    public String getUser(){

        return this.user;

    }

    public String getPassword(){

        return this.password;

    }

    public String getSQLType(){

        return this.sqltype;

    }

    public int getPort(){

        return this.port;

    }

    public ResultSet executeQuery(String query){

        ResultSet resultset;

        try {

            statement = connection.createStatement();
            resultset = statement.executeQuery(query);

            if(debugmode == true) {

                System.out.println("[DEBUG SQLAGENT] - SQL-запрос был выполнен.");

            }

            return resultset;

        } catch (SQLException throwables) {

            throwables.printStackTrace();

        }

        return null;

    }

    public void setUser(String user){

        this.user = user;

    }

    public void setPassword(String password){

        this.password = password;

    }

    public void setSQLType(String SQLType){

        this.sqltype = SQLType;

    }

    public void setPort(int port){

        this.port = port;

    }

    @Override
    public void setDebugmode(boolean debugmode){

        this.debugmode = debugmode;

    }

    @Override
    public void connect(){

        try {

            String urls = "jdbc:" + sqltype + "://" + url + ":" + port;
            String username = user;
            String passwords = password;
            Class.forName("com." + sqltype + ".cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            
            this.connection = DriverManager.getConnection(urls, username, passwords);

            if(debugmode == true) {

                System.out.println("[DEBUG SQLAGENT] - Подключение к базе данных выполнено успешно.");

            }

        } catch(Exception ex) {

            if(debugmode == true) {

                System.out.println("[DEBUG SQLAGENT] - Подключение провалено. Проверьте данные подключения.");

            }

        }

    }

    @Override
    public void executeTask(String task) {

        try {

            statement = connection.createStatement();
            statement.executeUpdate(task);

            if(debugmode == true) {

                System.out.println("[DEBUG SQLAGENT] - Задача была успешно выполнена.");

            }

        } catch (SQLException throwables) {

            throwables.printStackTrace();

        }

    }

    @Override
    public void closeConnection() {

        try {

            connection.close();

            if(debugmode == true) {

                System.out.println("[DEBUG SQLAGENT] - Отключение от базы данных прошло успешно.");

            }

        } catch (SQLException throwables) {

            throwables.printStackTrace();

        }

    }

    @Override
    public void setData(String user, String password, String sqltype, int port) {

        this.user = user;
        this.password = password;
        this.port = port;
        this.sqltype = sqltype;

    }
    
}
