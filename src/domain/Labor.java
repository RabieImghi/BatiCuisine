package domain;

public class Labor extends Component {
    private int idLabor;
    private double hourlyRate;
    private double hoursWorked;
    private double workerProductivity;

    public Labor(String name, String componentType, double vatRate, double hourlyRate, double hoursWorked, double workerProductivity,Project project) {
        super(name, componentType, vatRate,project);
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
        this.workerProductivity = workerProductivity;
    }
    public int getIdLabor() {
        return idLabor;
    }

    public void setIdLabor(int idLabor) {
        this.idLabor = idLabor;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public double getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(double hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    public double getWorkerProductivity() {
        return workerProductivity;
    }

    public void setWorkerProductivity(double workerProductivity) {
        this.workerProductivity = workerProductivity;
    }

    @Override
    public String toString() {
        return super.toString()+"\nLabor :--------------\n " +
                "idLabor=" + idLabor +
                "\nhourlyRate=" + hourlyRate +
                "\nhoursWorked=" + hoursWorked +
                "\nworkerProductivity=" + workerProductivity;
    }
}
