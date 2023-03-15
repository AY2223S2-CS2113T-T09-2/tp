package validation.order;

import app.Command;
import exception.OrderException;
import item.Menu;
import utility.Ui;
import validation.Validation;

/**
 * Handles order related input validation
 */
public class AddOrderValidation extends Validation {
    private Ui ui = new Ui();
    private Menu menu = new Menu();

    /**
     * Catch all the thrown exception and display the error message accordingly
     *
     * @param arg user command
     * @throws OrderException custom exception for order validation
     */
    public void validateCommand(Command arg) throws OrderException {
        try {
            checkValidFlag(arg);
            checkArgumentPresent(arg);
            checkValidFlagArgument(arg);
            validateAddOrder(arg, menu);
        } catch(OrderException o) {
            throw new OrderException(o.getMessage());
        }
    }

    public void validateAddOrder(Command arg, Menu menu) throws OrderException {
        String itemIndex = "";

        if(arg.getArgumentString().contains("-i")) {
            itemIndex = arg.getArgumentMap().get("i").trim();
        } else if(arg.getArgumentString().contains("--item")) {
            itemIndex = arg.getArgumentMap().get("item").trim();
        }

        if(!isValidIndex(itemIndex, menu)) {
            throw new OrderException(ui.getInvalidIndex());
        } else if(!(isValidQuantity(arg))) {
            throw new OrderException(ui.getInvalidOrderInteger());
        }
    }

    /**
     * Check if the argument after flag is valid
     *
     * @param arg user command
     * @throws OrderException custom exception for order validation
     */
    public void checkValidFlagArgument(Command arg) throws OrderException {
        if(arg.getArgumentString().contains("-i") || arg.getArgumentString().contains("--item")) {
            if(!(isInteger(arg.getArgumentMap().get("i").trim()))
                    || !(isInteger(arg.getArgumentMap().get("item").trim()))) {
                throw new OrderException(ui.getInvalidOrderInteger());
            }
        }

        if(arg.getArgumentString().contains("-q") || arg.getArgumentString().contains("--quantity")) {
            if(!(isInteger(arg.getArgumentMap().get("q").trim()))
                    || !(isInteger(arg.getArgumentMap().get("quantity").trim()))) {
                throw new OrderException(ui.getInvalidOrderInteger());
            }
        }
    }

    /**
     * Check if the required flags are present
     *
     * @param arg user command
     * @throws OrderException custom exception for order validation
     */
    public void checkValidFlag(Command arg) throws OrderException {

        if(arg.getArgumentString().contains("-i") || arg.getArgumentString().contains("--item")) {

        } else {
            throw new OrderException(ui.getMissingOrderFlag());
        }

    }

    /**
     * Check if there are argument present after the required flags
     *
     * @param arg user input
     * @throws OrderException custom exception for order validation
     */
    public void checkArgumentPresent(Command arg) throws OrderException {
        if(arg.getArgumentString().contains("-i") || arg.getArgumentString().contains("--item")) {
            if(arg.getArgumentMap().get("i").length() < 1) {
                throw new OrderException(ui.getMissingOrderArgument());
            }
        }

    }

    /**
     * Check if the input is integer
     *
     * @param input the user input after flags
     * @return validation outcome (true/false)
     */
    public boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
        } catch(NumberFormatException n) {
            return false;
        }

        return true;
    }

    /**
     * Check if the quantity is more than 0
     *
     * @param arg user input
     * @return validation outcome (true/false)
     */
    public boolean isValidQuantity(Command arg) {
        int quantity = 0;
        if(arg.getArgumentString().contains("-q")) {
            quantity = Integer.parseInt(arg.getArgumentMap().get("q").trim());
        } else if(arg.getArgumentString().contains("--quantity")) {
            quantity = Integer.parseInt(arg.getArgumentMap().get("quantity").trim());
        } else {
            return true;
        }
        if(quantity <= 0) {
            return false;
        }
        return true;
    }


}
