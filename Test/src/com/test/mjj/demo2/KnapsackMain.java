package com.test.mjj.demo2;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.io.*;
import java.util.*;
import java.util.List;

public class KnapsackMain {
    public static void main(String[] args) {
        //读文件
        String root = "C:\\Users\\lenovo\\Desktop\\demo";
        ArrayList<FileBean> files = readDataFromFile(root);
        Scanner in = new Scanner(System.in);
        int op = 0;
        while (true) {
            if (op == -1) {
                break;
            }
            System.out.println("********************欢迎使用0-1背包问题解决系统******************");
            System.out.println("请输入数字选择你的操作");
            System.out.println("1:绘制重量-价值图");
            System.out.println("2:按重量非递增排序");
            System.out.println("3:动态规划算法解决");
            System.out.println("4:贪心算法解决");
            System.out.println("5:回溯算法解决");
            System.out.println("6:退出系统");
            op = in.nextInt();
            switch (op) {
                case 1: {
                    System.out.println("请选择数据源");
                    for (int i = 0; i < files.size(); i++) {
                        System.out.println(i + ":" + files.get(i).getName());
                    }
                    int index = in.nextInt();
                    if (index < files.size()) {
                        int data[][] = (int[][]) files.get(index).getData().get(files.get(index).getName());
                        //绘制散点图
                        new Picture(data);
                    } else {
                        System.out.println("输入有误");
                    }
                    break;
                }
                case 2: {
                    System.out.println("请选择数据源");
                    for (int i = 0; i < files.size(); i++) {
                        System.out.println(i + ":" + files.get(i).getName());
                    }
                    int index = in.nextInt();
                    if (index < files.size()) {
                        int data[][] = (int[][]) files.get(index).getData().get(files.get(index).getName());
                        //按重量比排序
                        sortByWeight(data);
                        System.out.println("按质量非递增排序结果如下：");
                        for (int i = 0; i < data.length; i++) {
                            System.out.println(data[i][0] + "\t" + data[i][1]);
                        }
                    } else {
                        System.out.println("输入有误");
                    }
                    break;
                }
                case 3: {
                    System.out.println("请选择数据源");
                    for (int i = 0; i < files.size(); i++) {
                        System.out.println(i + ":" + files.get(i).getName());
                    }
                    int index = in.nextInt();
                    if (index < files.size()) {
                        int data[][] = (int[][]) files.get(index).getData().get(files.get(index).getName());
                        //动态规划
                        long start=System.currentTimeMillis();
                        DP dp = new DP(data, files.get(index).getTotalWeight());
                        //初始化背包
                        int[][] initpkdata = dp.initpkdata();
                        int[][] result = dp.calculate(initpkdata);
                        ArrayList<Integer> list = dp.findproducts(result);
                        long end=System.currentTimeMillis();
                        writeData2Fime(root + "\\" + files.get(index).getName(), data, list, files.get(index).getTotalWeight(),(end-start),"动态规划");
                    } else {
                        System.out.println("输入有误");
                    }
                    break;
                }
                case 4: {
                    System.out.println("请选择数据源");
                    for (int i = 0; i < files.size(); i++) {
                        System.out.println(i + ":" + files.get(i).getName());
                    }
                    int index = in.nextInt();
                    if (index < files.size()) {
                        int data[][] = (int[][]) files.get(index).getData().get(files.get(index).getName());
                        long start=System.currentTimeMillis();
                        Greedy greedy = new Greedy(data, files.get(index).getTotalWeight());
                        ArrayList<Integer> list = greedy.calculate();
                        long end=System.currentTimeMillis();
                        writeData2Fime(root + "\\" + files.get(index).getName(), data, list, files.get(index).getTotalWeight(),(end-start),"贪心算法");
                    } else {
                        System.out.println("输入有误");
                    }
                    break;
                }
                case 5: {
                    System.out.println("请选择数据源");
                    for (int i = 0; i < files.size(); i++) {
                        System.out.println(i + ":" + files.get(i).getName());
                    }
                    int index = in.nextInt();
                    if (index < files.size()) {
                        int data[][] = (int[][]) files.get(index).getData().get(files.get(index).getName());
                        long start=System.currentTimeMillis();
                        Back back = new Back(data, files.get(index).getTotalWeight());
                        back.solve(0);
                        long end=System.currentTimeMillis();
                        ArrayList<Integer> list = back.getLists();
                        writeData2Fime(root + "\\" + files.get(index).getName(), data, list, files.get(index).getTotalWeight(),(end-start),"回溯算法");
                    } else {
                        System.out.println("输入有误");
                    }
                    break;
                }
                case 6: {
                    op = -1;
                    break;
                }
                default:
                    break;

            }
        }

    }

    private static void writeData2Fime(String path, int[][] data, ArrayList<Integer> list, int totalWeight, long time, String model) {
        File file = new File(path + ".txt");
        sortByWeight(data);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(model+"算法解决0-1背包问题总耗时为"+time+"秒");
            bw.newLine();
            bw.write("商品信息如下：");
            bw.newLine();
            bw.write("重量：");
            for (int j = 0; j < data.length; j++) {
                bw.write(data[j][0] + "\t");
            }
            bw.newLine();
            bw.write("价值：");
            for (int j = 0; j < data.length; j++) {
                bw.write(data[j][1] + "\t");
            }
            bw.newLine();
            bw.write("放入背包的物品：");
            for (int j = 0; j < list.size(); j++) {
                bw.write(list.get(j) + "\t");
            }
            bw.newLine();
            bw.write("放入背包的物品重量：");
            int weight = 0;
            for (int j = 0; j < list.size(); j++) {
                bw.write(data[list.get(j) - 1][0] + "\t");
                weight += data[list.get(j) - 1][0] - 1;
            }
            bw.write("总重量：" + weight);
            bw.newLine();
            bw.write("放入背包的物品价值：");
            int value = 0;
            for (int j = 0; j < list.size(); j++) {
                bw.write(data[list.get(j) - 1][1] - 1 + "\t");
                value += data[list.get(j) - 1][1] - 1;
            }
            bw.write("总价值：" + value);
            bw.newLine();
            bw.write("背包剩余容量：" + (totalWeight - weight));
            bw.flush();
            bw.close();
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            System.out.println("结果已写入" + file.getAbsolutePath() + "文件中");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void sortByWeight(int[][] data) {
        for (int i = 0; i < data.length; i++) {
            //选择排序
            //首先在未排序的数列中找到最小(or最大)元素，然后将其存放到数列的起始位置；
            //接着，再从剩余未排序的元素中继续寻找最小(or最大)元素，然后放到已排序序列的末尾。
            //以此类推，直到所有元素均排序完毕。
            for (int j = i + 1; j < data.length; j++) {
                if (data[j][0] > data[i][0]) {
                    int temp1 = data[j][0];
                    data[j][0] = data[i][0];
                    data[i][0] = temp1;
                    int temp2 = data[j][1];
                    data[j][1] = data[i][1];
                    data[i][1] = temp2;
                }
            }
        }
    }

    private static ArrayList<FileBean> readDataFromFile(String root) {
        ArrayList<FileBean> fileLists = new ArrayList<FileBean>();
        File rootPath = new File(root);
        if (rootPath.exists()) {
            File files[] = rootPath.listFiles();
            if (files == null || files.length == 0) {
                System.out.println("文件夹为空");
                return null;
            } else {
                String realName = null;
                Map realMap = null;
                for (File item : files
                ) {
                    if (item.isFile()) {
                        String name = item.getName();
                        String type = name.substring(name.lastIndexOf(".") + 1);
                        int totalWeight = 0;
                        if (type.equals("in")) {
                            try {
                                realName = name;
                                BufferedReader br = new BufferedReader(new FileReader(item));
                                String line;
                                List lists = new ArrayList<>();
                                while ((line = br.readLine()) != null) {
                                    lists.add(line);
                                }
                                br.close();
                                int i = 0;
                                int data[][] = new int[lists.size()][2];
                                Iterator<String> iterator = lists.iterator();
                                while (iterator.hasNext()) {
                                    String array[] = iterator.next().split(" ");
                                    data[i][0] = Integer.valueOf(array[0]);
                                    data[i][1] = Integer.valueOf(array[1]);
                                    i++;
                                }
                                Map map = new HashMap<>();
                                map.put(name, data);
                                realMap = map;
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                                return null;
                            } catch (IOException e) {
                                e.printStackTrace();
                                return null;
                            }
                        }
                        if (type.equals("out")) {
                            try {
                                BufferedReader br = new BufferedReader(new FileReader(item));
                                String line;
                                while ((line = br.readLine()) != null) {
                                    totalWeight = Integer.valueOf(line.toString().trim());
                                }
                                br.close();
                                FileBean bean = new FileBean();
                                bean.setData(realMap);
                                bean.setName(realName);
                                bean.setTotalWeight(totalWeight);
                                fileLists.add(bean);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                                return null;
                            } catch (IOException e) {
                                e.printStackTrace();
                                return null;
                            }
                        }
                    }
                }
                return fileLists;
            }
        }
        return null;
    }

    static class Picture extends JPanel {
        int DIS = 30;
        int data[][];

        public Picture(int data[][]) {
            this.data = data;
            JFrame frame = new JFrame("散点图");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(this);
            frame.setSize(300, 300);
            frame.setLocation(200, 200);
            frame.setVisible(true);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D draw = (Graphics2D) g;
            draw.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int w = getWidth();
            int h = getHeight();
            draw.draw(new Line2D.Double(DIS, DIS, DIS, h - DIS));
            draw.drawString("价值", DIS - 10, DIS);
            draw.draw(new Line2D.Double(DIS, h - DIS, w - DIS, h - DIS));
            draw.drawString("重量", w - DIS, h - DIS);
            int ymin = (h - 2 * DIS) / data.length;
            int xmin = (w - 2 * DIS) / data.length;
            draw.setPaint(Color.black);
            int ydis = ymin;
            int xdis = xmin;
            for (int i = 0; i < data.length; i++) {
                int x = data[i][0];
                int y = data[i][1];
                if (i != data.length - 1) {
                    draw.drawString(xdis + "", xdis + DIS, h - DIS + 15);
                    draw.drawString(ydis + "", DIS - 20, h - (ydis + DIS));
                    xdis += xmin;
                    ydis += ymin;
                }
                draw.fill(new Ellipse2D.Double(x + DIS, h - (y + DIS), 4, 4));

            }

        }

    }

    static class DP {

        // 记录背包的总容量
        public int packageweight;
        // 记录商品总数
        public int productnum;
        // 记录每个商品的重量
        public ArrayList<Integer> weights;
        // 记录每个商品的价值
        public ArrayList<Integer> values;
        // 记录存放的物品
        public ArrayList<Integer> lists;

        public DP(int data[][], int weight) {
            packageweight = weight;
            productnum = data.length;
            weights = new ArrayList<Integer>();
            values = new ArrayList<Integer>();
            lists = new ArrayList<Integer>();
            for (int i = 0; i < productnum; i++) {
                weights.add(data[i][0]);
                values.add(data[i][1]);
            }
        }

        /**
         * 初始化背包问题（记录价值的表格）
         * m[i][0] = 0 :表示背包重量为0，不能装东西，因此价值全为0
         * m[0][j] = 0 :表示没有可以装的物品，因此价值为0
         */
        public int[][] initpkdata() {
            int[][] m = new int[this.productnum + 1][this.packageweight + 1];

            for (int i = 0; i <= this.productnum; i++) {
                m[i][0] = 0;
            }
            for (int j = 0; j <= this.packageweight; j++) {
                m[0][j] = 0;
            }

            return m;
        }

        /**
         * 计算背包问题
         *
         * @return 修改相应价值记录后的表
         */
        public int[][] calculate(int[][] arr) {

            for (int i = 1; i <= this.productnum; i++) {
                for (int j = 1; j <= this.packageweight; j++) {

                    // 当第i件物品重量大于当前包的容量 则放不进去
                    // 所以当前背包所含价值等于前i-1件商品的价值
                    if (this.weights.get(i - 1) > j) {
                        arr[i][j] = arr[i - 1][j];
                    }
                    // 当第i件商品能放进去时
                    // 1 放入商品，价值为：arr[i-1][j-(int)this.weights.get(i-1)] + (int)this.values.get(i-1)
                    // 2不放入商品，价值为前i-1件上篇价值和：arr[i][j] = arr[i-1][j];
                    // 此时最大价值为上述两种方案中最大的一个
                    else {
                        if (arr[i - 1][j] < arr[i - 1][j - this.weights.get(i - 1)] + this.values.get(i - 1)) {
                            arr[i][j] = arr[i - 1][j - this.weights.get(i - 1)] + this.values.get(i - 1);
                        } else {
                            arr[i][j] = arr[i - 1][j];
                        }
                    }
                }
            }

            return arr;
        }

        /**
         * 查找那些商品放在背包中
         *
         * @return
         */
        public ArrayList<Integer> findproducts(int[][] arr) {
            int j = this.packageweight;
            for (int i = this.productnum; i > 0; i--) {
                if (arr[i][j] > arr[i - 1][j]) {
                    lists.add(i);
                    j = j - this.weights.get(i - 1);
                    if (j < 0) {
                        break;
                    }
                }
            }
            return lists;
        }
    }

    static class Greedy {
        //记录背包容量
        private int packageWeight;
        // 记录商品
        private int[][] data;

        public Greedy(int data[][], int weight) {
            packageWeight = weight;
            this.data = data;
        }

        //计算背包问题
        public ArrayList<Integer> calculate() {
            //存放密度
            double[] performance = new double[data.length];
            //存放索引的数组
            int[] index = new int[data.length];
            for (int i = 0; i < performance.length; i++) {
                performance[i] = data[i][1] / data[i][0];
                index[i] = i;
            }
            //冒泡排序
            for (int i = 0; i < performance.length - 1; i++) {
                for (int j = 0; j < performance.length - 1 - i; j++) {
                    if (performance[j] < performance[j + 1]) {
                        double temp = performance[j];
                        performance[j] = performance[j + 1];
                        performance[j + 1] = temp;
                        //索引跟着交换
                        int temp1 = index[j];
                        index[j] = index[j + 1];
                        index[j + 1] = temp1;
                    }
                }
            }

            //计算最优解
            ArrayList<Integer> lists = new ArrayList<Integer>();
            for (int i = 0; i < index.length; i++) {
                if (data[index[i]][0] <= packageWeight) {
                    lists.add(index[i] + 1);
                    packageWeight -= data[index[i]][0];
                }
            }
            return lists;
        }

    }

    static class Back {
        // 背包的总承重
        private int packageWeight;
        // 记录商品
        private int[][] data;
        // 背包的当前承重
        private int currWeight;
        // 记录索引
        private int index[];
        // 放入物品后背包的最优价值
        private int bestValue;
        // 放入物品和背包的当前价值
        private int currValue;
        //记录背包存放记录
        private ArrayList<Integer> lists = new ArrayList<>();

        public Back(int data[][], int weight) {
            this.packageWeight = weight;
            this.data = data;
            this.index = new int[data.length];
            for (int i = 0; i < data.length; i++) {
                index[i] = i + 1;
            }
            //按照单位质量排序
            sortByPerWeight();

        }

        private void sortByPerWeight() {
            //冒泡排序
            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data.length - 1 - i; j++) {
                    if ((data[j][1] / data[j][0]) < (data[j + 1][1] / data[j + 1][0])) {
                        int temp1 = data[j][0];
                        data[j][0] = data[j + 1][0];
                        data[j + 1][0] = temp1;
                        int temp2 = data[i][1];
                        data[j][1] = data[j + 1][1];
                        data[j + 1][1] = temp2;
                        //交换索引
                        int temp3 = index[j];
                        index[j] = index[j + 1];
                        index[j + 1] = temp3;
                    }
                }
            }
        }

        public int solve(int i) {

            // 当没有物品可以放入背包时，当前价值为最优价值
            if (i >= data.length) {
                bestValue = currValue;
                return bestValue;
            }

            // 首要条件：放入当前物品，判断物品放入背包后是否小于背包的总承重
            if (currWeight + data[i][0] <= packageWeight) {
                // 将物品放入背包中的状态
                lists.add(index[i]);
                currWeight += data[i][0];
                currValue += data[i][1];
                // 选择下一个物品进行判断
                bestValue = solve(i + 1);
            }

            // 次要条件：不放入当前物品，放入下一个物品可能会产生更优的价值，则对下一个物品进行判断
            // 当前价值+剩余价值<=最优价值，不需考虑右子树情况，由于最优价值的结果是由小往上逐层返回，
            // 为了防止错误的将单位重量价值大的物品错误的剔除，需要将物品按照单位重量价值从大到小进行排序
            if (currValue + getSurplusValue(i + 1) > bestValue) {
                // 选择下一个物品进行判断
                bestValue = solve(i + 1);
            }
            return bestValue;
        }

        // 获得物品的剩余总价值
        public int getSurplusValue(int i) {
            int surplusValue = 0;
            for (int j = i; j < data.length; j++)
                surplusValue += data[i][1];
            return surplusValue;
        }
        //获取背包存放记录


        public ArrayList<Integer> getLists() {
            return lists;
        }
    }

    static class FileBean {
        private String name;
        private int totalWeight;
        private Map data;

        public void setData(Map data) {
            this.data = data;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setTotalWeight(int totalWeight) {
            this.totalWeight = totalWeight;
        }

        public int getTotalWeight() {
            return totalWeight;
        }

        public Map getData() {
            return data;
        }

        public String getName() {
            return name;
        }
    }
}


