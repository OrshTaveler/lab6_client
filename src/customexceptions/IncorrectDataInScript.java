package customexceptions;
/**
 * Класс исключение, сигнализирующий об ошибке в файле скрипта.
 * @author Ubica228
 */
public class IncorrectDataInScript extends Exception{
    public IncorrectDataInScript(String description){
        super(description);
    }
}
