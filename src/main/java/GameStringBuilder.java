public class GameStringBuilder {

    public String buildGameString(Game game){

        int height = game.getHeight();
        int width = game.getWidth();
        int[][] field = game.getField();

        StringBuilder stringBuilder = new StringBuilder();

        if (game.isGameOver()){
            stringBuilder.append("Game over").append(System.lineSeparator());
        }

        stringBuilder.append("Your score: ").append(game.getScore()).append(System.lineSeparator());

        stringBuilder.append("<pre>");

        String rowSeparator = "|------|------|------|------|";

        stringBuilder.append(rowSeparator).append(System.lineSeparator());

        for (int currentRow = 0; currentRow < height; currentRow++){

            stringBuilder.append("|");

            for (int currentColumn = 0; currentColumn < width; currentColumn++){

                int currentValue = field[currentRow][currentColumn];

                stringBuilder.append(formatToLength(currentValue, 6)).append("|");
            }

            stringBuilder.append(System.lineSeparator()).append(rowSeparator).append(System.lineSeparator());

        }
        stringBuilder.append("</pre>");

        return stringBuilder.toString();

    }

    private String formatToLength(int number, int length){

        String stringNumber = "";

        if (number == 0) {
            stringNumber = " ";
        } else {
            stringNumber = String.valueOf(number);
        }

        if (stringNumber.length() != length){

            StringBuilder stringBuilder = new StringBuilder(stringNumber);

            boolean prefix = false;

            while (stringBuilder.length() != length){

                if (prefix) {
                    stringBuilder.insert(0, " ");
                } else {
                    stringBuilder.append(" ");
                }

                prefix = !prefix;

            }

            stringNumber = stringBuilder.toString();

        }

        return stringNumber;

    }

}
