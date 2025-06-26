public class Student {
    public String name;
    public int age;
    private Hashmap<String,Student> friends = new HashMap<>();

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void addFriend(String name, Student friend) {
        friends.put(name, friend);
    }
    public Student getFriend(String name) {
        return friends.get(name);
    }
}
