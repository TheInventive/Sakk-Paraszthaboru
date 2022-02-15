import hu.chess.dao.GameDAO;
import hu.chess.dao.GameDAOImpl;
import hu.chess.model.Game;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/BaseServlet")
public class BaseServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        GameDAO gameDAO = new GameDAOImpl();
        List<Game> gameList = gameDAO.findAll();

        try {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();

            ServletContext context = getServletContext();
            context.setAttribute("Sakk","Sakk");

            out.println("""
                    <!DOCTYPE html>
                    <html>
                    <head>
                    <meta name="viewport" content="width=device-width, initial-scale=1">
                    <style>
                    * {
                      box-sizing: border-box;
                    }

                    #myInput {
                      background-position: 10px 12px;
                      background-repeat: no-repeat;
                      width: 100%;
                      font-size: 16px;
                      padding: 12px 20px 12px 40px;
                      border: 1px solid #ddd;
                      margin-bottom: 12px;
                    }

                    #myUL {
                      list-style-type: none;
                      padding: 0;
                      margin: 0;
                    }

                    #myUL li a {
                      border: 1px solid #ddd;
                      margin-top: -1px; /* Prevent double borders */
                      background-color: #f6f6f6;
                      padding: 12px;
                      text-decoration: none;
                      font-size: 18px;
                      color: black;
                      display: block
                    }

                    #myUL li a:hover:not(.header) {
                      background-color: #eee;
                    }
                    </style>
                    </head>
                    <body>

                    <h2>Játékok</h2>

                    <input type="text" id="myInput" onkeyup="myFunction()" placeholder="Játék keresése..." title="Type in a name">

                    <ul id="myUL">
                    """);
            String winner = "";
            for (Game game : gameList) {
                if (game.getWinner() == 1)
                    winner = "\tNyertes: " + game.getWhitePlayerName();
                else if (game.getWinner() == 2)
                    winner = "\tNyertes: " + game.getBlackPlayerName();
                else if (game.getWinner() == 3)
                    winner = "\tDöntetlen!";
                else
                    winner = "";
                String gameString = game.getGameId() + " " + game.getWhitePlayerName() + " VS "
                        + game.getBlackPlayerName() + " at " + game.getTimeOfPlay() + winner;
                out.println(String.format("<li><a href=\"#\">%s</a></li>\n", gameString));
            }
            out.println(
                    """
                            </ul>
                            <script>
                            function myFunction() {
                                var input, filter, ul, li, a, i, txtValue;
                                input = document.getElementById("myInput");
                                filter = input.value.toUpperCase();
                                ul = document.getElementById("myUL");
                                li = ul.getElementsByTagName("li");
                                for (i = 0; i < li.length; i++) {
                                    a = li[i].getElementsByTagName("a")[0];
                                    txtValue = a.textContent || a.innerText;
                                    if (txtValue.toUpperCase().indexOf(filter) > -1) {
                                        li[i].style.display = "";
                                    } else {
                                        li[i].style.display = "none";
                                    }
                                }
                            }
                            </script>

                            </body>
                            </html>

                            """);
            out.close();
        } catch(Exception e){
            response.getWriter().println(e);
        }
    }


    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        super.service(req, res);
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}