import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Random;

class Move {
    String name;
    int value;

    Move(String name, int value) {
        this.name = name;
        this.value = value;
    }
}

public class ShowMoveServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // ゲームの進行状況を保存
        int PP = 0; // 勝ち
        int EP = 0; // 負け
        int DP = 0; // あいこ
        int i = 0;

        // レスポンスの内容をHTMLとして設定
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // ゲーム開始の案内
        out.println("<html>");
        out.println("<head><title>じゃんけんゲーム</title></head>");
        out.println("<body>");
        out.println("<h1>じゃんけんをしましょう</h1>");

        // ゲームのループ
        while (i++ < 10) {
            out.println("<h2>第" + i + "回戦目</h2>");
            out.println("<p>あなたの手を入力してください（1: グー, 2: チョキ, 3: パー）</p>");

            // リクエストパラメータを取得（ユーザーの入力）
            String input = request.getParameter("hand");
            Random random = new Random();

            // ゲームの手の選択肢
            String G = "グー";
            String C = "チョキ";
            String P = "パー";

            String Won = "あなたの勝ちです。";
            String Lose = "あなたの負けです。";
            String Draw = "あいこです。";
            int output = 4;

            // ユーザーの入力が有効かどうか確認
            if (input != null && (input.equals("1") || input.equals("2") || input.equals("3"))) {
                // 入力を数値に変換
                if (input.equals("1")) {
                    output = 1;
                } else if (input.equals("2")) {
                    output = 2;
                } else {
                    output = 3;
                }

                // プレイヤーと相手の手を設定
                Move player = new Move(G, output);
                Move enemy = new Move(G, random.nextInt(3) + 1);

                if (enemy.value == 1) {
                    enemy.name = G;
                } else if (enemy.value == 2) {
                    enemy.name = C;
                } else {
                    enemy.name = P;
                }

                if (player.value == 1) {
                    player.name = G;
                } else if (player.value == 2) {
                    player.name = C;
                } else {
                    player.name = P;
                }

                // 結果の表示
                out.println("<p>あなたの手: " + player.name + " (" + player.value + ")</p>");
                out.println("<p>相手の手: " + enemy.name + " (" + enemy.value + ")</p>");

                // 勝敗判定
                if (player.value == 3 && enemy.value == 1) {
                    out.println("<p>" + Won + "</p>");
                    PP++;
                } else if (enemy.value == player.value + 1 || (player.value == 3 && enemy.value == 1)) {
                    out.println("<p>" + Won + "</p>");
                    PP++;
                } else if (player.value == enemy.value) {
                    out.println("<p>" + Draw + "</p>");
                    DP++;
                } else {
                    out.println("<p>" + Lose + "</p>");
                    EP++;
                }

                out.println("<hr>");
            } else {
                out.println("<p>無効な入力です。もう一度試してください。</p>");
            }
        }

        // ゲームの結果表示
        out.println("<h2>今回の戦績</h2>");
        out.println("<p>勝ち: " + PP + " 負け: " + EP + " あいこ: " + DP + "</p>");
        out.println("</body>");
        out.println("</html>");
    }
}
