package domain;
public class Component {
    private int id;
    private String name;
    private String componentType;
    private double vatRate;
    private Project project;

    public Component(String name, String componentType, double vatRate,Project project) {
        this.name = name;
        this.componentType = componentType;
        this.vatRate = vatRate;
        this.project = project;
    }

    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getComponentType() {
      return componentType;
    }

    public void setComponentType(String componentType) {
      this.componentType = componentType;
    }

    public double getVatRate() {
      return vatRate;
    }

    public void setVatRate(double vatRate) {
      this.vatRate = vatRate;
    }

    public Project getProject() {
      return project;
    }

    public void setProject(Project project) {
      this.project = project;
    }

    @Override
    public String toString() {
        return "Component : ==============================================\n" +
                "name='" + name + '\'' +
                "\ncomponentType='" + componentType + '\'' +
                "\nvatRate=" + vatRate +
                "\nproject=" + project;
    }
}
