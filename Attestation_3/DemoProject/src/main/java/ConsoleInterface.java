import java.util.Scanner;

public class ConsoleInterface {

    public ConsoleInterface() {}

    private TreePainter painter = new TreePainter();

    public TreePainter getPainter() {
        return painter;
    }

    public void setPainter(TreePainter painter) {
        this.painter = painter;
    }

    public void start() throws Exception {
        Scanner scanner = new Scanner(System.in);
        TreePainter painter = new TreePainter();
        int n = 0;
        while (n != 1) {
            System.out.println("Choose class for work : TreeMap or BidiMap. Write 'end' if you want to stop program");
            String strs = scanner.nextLine();
            if (strs.equals("TreeMap")) {
                int i = 0;
                TreeMap<Integer, String> treeMap = new TreeMap<>();
                System.out.println("Write root for tree");
                String[] root = scanner.nextLine().split(" ");
                treeMap.addElement(Integer.parseInt(root[0]), root[1]);
                while (i != 1) {
                    painter.drawTree(treeMap.getRoot());
                    System.out.println("Choose action with tree: 'add', 'remove', 'getValue', 'containsKey', " +
                            "'containsValue', 'clone', 'clear' or 'end' for stop work with this tree");
                    String str = scanner.nextLine();
                    if (str.equals("end")) {
                        i++;
                    } else if (str.equals("add")) {
                        System.out.println("Write 2 args for new element");
                        String[] node = scanner.nextLine().split(" ");
                        treeMap.addElement(Integer.parseInt(node[0]), node[1]);
                    } else if (str.equals("remove")) {
                        System.out.println("Write key of remove element");
                        treeMap.removeElement(Integer.parseInt(scanner.nextLine()));
                    } else if (str.equals("getValue")) {
                        System.out.println("Write key to find value");
                        System.out.println(treeMap.getValue(Integer.parseInt(scanner.nextLine())));
                    } else if (str.equals("containsKey")) {
                        System.out.println("Write key you want check");
                        System.out.println(treeMap.containsKey(Integer.parseInt(scanner.nextLine())));
                    } else if (str.equals("containsValue")) {
                        System.out.println("Write value you want check");
                        System.out.println(treeMap.containsValue(scanner.nextLine()));
                    } else if (str.equals("clone")) {
                        TreeMap<Integer, String> clonedTreeMap = treeMap.clone();
                        System.out.println("Cloned tree");
                        painter.drawTree(clonedTreeMap.getRoot());
                        System.out.println("Origin tree");
                    } else if (str.equals("clear")) {
                        treeMap.clear();
                    } else {
                        System.out.println("Incorrect command");
                    }
                }
            } else if (strs.equals("BidiMap")) {
                int i = 0;
                System.out.println("Write root for tree");
                String[] root = scanner.nextLine().split(" ");
                boolean isInversed = false;
                BidiMap<Integer, String> bidiMap = new BidiMap<>(Integer.parseInt(root[0]), root[1]);
                BidiMap<String, Integer> inversedBidiMap = bidiMap.getInverseBidiMap();
                BidiMap currBidiMap = bidiMap;
                while (i != 1) {
                    System.out.println("Origin tree");
                    painter.drawTree(currBidiMap.getOriginalMap().getRoot());
                    System.out.println("Inversed tree");
                    painter.drawTree(currBidiMap.getInverseMap().getRoot());
                    System.out.println("Choose action with tree: 'add', 'remove'," +
                            " 'containsValue', 'containsKey', 'getInversedMap' or 'end' to stop work with this tree");
                    String str = scanner.nextLine();
                    if (str.equals("end")) {
                        i++;
                    } else if (str.equals("add")) {
                        System.out.println("Write 2 args for new element");
                        String[] node = scanner.nextLine().split(" ");
                        if (isInversed) {
                            currBidiMap.add(node[0], Integer.parseInt(node[1]));
                        } else {
                            currBidiMap.add(Integer.parseInt(node[0]), node[1]);
                        }
                    } else if (str.equals("remove")) {
                        System.out.println("Write 'key' if you want to removeWithKey or " +
                                "'value' if you want to removeWithValue");
                        String arg = scanner.nextLine();
                        if (arg.equals("key")) {
                            System.out.println("Write key of remove element");
                            if (isInversed) {
                                currBidiMap.removeWithKey(scanner.nextLine());
                            } else {
                                currBidiMap.removeWithKey(Integer.parseInt(scanner.nextLine()));
                            }
                        } else if (arg.equals("value")) {
                            System.out.println("Write value of remove element");
                            currBidiMap.removeWithValue(scanner.nextLine());
                        } else {
                            System.out.println("Incorrect command");
                        }
                    } else if (str.equals("containsValue")) {
                        System.out.println("Write key to check value");
                        if (isInversed) {
                            System.out.println(currBidiMap.containsValue(scanner.nextLine()));
                        } else {
                            System.out.println(currBidiMap.containsValue(Integer.parseInt(scanner.nextLine())));
                        }
                    } else if (str.equals("containsKey")) {
                        System.out.println("Write value to check key");
                        if (isInversed) {
                            System.out.println(currBidiMap.containsKey(Integer.parseInt(scanner.nextLine()  )));
                        } else {
                            System.out.println(currBidiMap.containsKey(scanner.nextLine()));
                        }
                    } else if (str.equals("getInversedMap")) {
                        if (isInversed) {
                            currBidiMap = bidiMap;
                            isInversed = false;
                        } else {
                            currBidiMap = inversedBidiMap;
                            isInversed = true;
                        }
                    } else {
                        System.out.println("Incorrect command");
                    }
                }
            } else if (strs.equals("end")) {
                n++;
            } else {
                System.out.println("Incorrect command");
            }
        }
    }
}
