import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Stack;

public class Calculator extends JFrame {
    private final String[] button_captions = {"9", "8","7","/","6","5","4","*","3","2","1","+","C","0",".","="};
    private final JButton[] buttons = new JButton[button_captions.length];
    private String equation = "";


    public Calculator(){
        super("Calculator"); // super and set window title
        this.setDefaultCloseOperation(EXIT_ON_CLOSE); // set close button behaviour
        this.setBounds(100,100,300,600); // set window size
        this.getContentPane().setLayout(new GridLayout(4,4)); // add the grid for buttons
        // initialise the buttons
        for(int i=0;i<button_captions.length;i++){
            buttons[i] = new JButton(); // initialise object
           this.add(makeButton(i)); // customize object and add to screen
        }
        this.setVisible(true); // make window visible
    }

    private JButton makeButton(int i){
        buttons[i].setText(button_captions[i]); // set button text
        buttons[i].addActionListener(actionEvent -> { // add on press behaviour
            // add what the button says to the equation unless special function
            switch (button_captions[i]){
                case "=" -> calculate();
                case "C" -> equation = "";
                default -> equation += button_captions[i];
            }
        });
        return buttons[i];
    }
    private int getOrderValue(String symbol){
        return switch (symbol) {
            case "+", "-" -> 1;
            case "*", "/" -> 2;
            default -> -1;
        };
    }

    //TODO: Finish this. Unisn RPN just evaluate on every symbol pop
    private void calculate(){
        System.out.println(equation);
        // the two stacks for the RPN
        Stack<String> mainStack = new Stack<>();
        Stack<String> symbolStack = new Stack<>();
        // string for storing multi digit numbers
        String tmp = "";
        // loop through the written equation
        for(int i=0;i<equation.length();i++){
            System.out.println(equation.charAt(i));
            // if digit add to memory (in case multi digit number)
            if(equation.charAt(i)>='0'&&equation.charAt(i)<='9'){
                tmp += equation.charAt(i);
            }
            else{ // symbol found
                mainStack.push(/*Integer.valueOf(*/tmp); // flush memory
                // if stack empty or top operator order val >= of found symbol
                while(!symbolStack.empty() &&
                        getOrderValue(String.valueOf(equation.charAt(i)))<= getOrderValue(symbolStack.peek())){
                    // add to output stack the previous operator
                    mainStack.add(symbolStack.pop());
                }
                // add current symbol to stack
                symbolStack.push(String.valueOf(equation.charAt(i)));
                // at the end reset tmp
                tmp = "";

            }
        }
        // add the last digit back on stack
        if(tmp!=""){
            mainStack.push(tmp);
        }
        // empty the secondary stack
        while(!symbolStack.empty()){
            mainStack.add(symbolStack.pop());
        }

        System.out.println(mainStack);
    }

    public static void main(String[] args) throws InvocationTargetException, InterruptedException {
        SwingUtilities.invokeAndWait(Calculator::new);
    }
}