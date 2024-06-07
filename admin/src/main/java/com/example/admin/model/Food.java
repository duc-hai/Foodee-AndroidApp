package com.example.admin.model;

import java.io.Serializable;

public class Food implements Serializable {
    private String image;
    private int id;
    //private ImageView image;
    private String foodName;
    private int price;
    private String infor;
    private String details;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    //    public ImageView getImage() {
//        return image;
//    }
//
//    public void setImage(ImageView image) {
//        this.image = image;
//    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getInfor() {
        return infor;
    }

    public void setInfor(String infor) {
        this.infor = infor;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

//    public static List <Food> init () {
//        String [] names = {"Bún đậu mắm tôm", "Cháo lòng", "Cơm tấm", "Bánh đa cua", "Bánh mì chảo", "Pizza"};
//        int [] pictures = {R.drawable.bun_dau, R.drawable.chao_long, R.drawable.com_tam, R.drawable.banh_da_cua, R.drawable.banh_mi_chao, R.drawable.pizza};
//        int [] prices = {45000, 20000, 30000, 35000, 25000, 80000};
//        String [] infors = {"Information 1", "Information 2", "Information 3", "Information 4", "Information 5", "Information 6"};
//        String [] details = {"Bún đậu mắm tôm là món ăn đơn giản, dân dã trong ẩm thực miền Bắc Việt Nam. Đây là món thường được dùng như bữa ăn nhẹ, ăn chơi. Thành phần chính gồm có bún tươi, đậu hũ chiên vàng, chả cốm, nem chua, mắm tôm pha chanh, ớt và ăn kèm với các loại rau thơm như tía tô, kinh giới, rau húng, xà lách, cà pháo ...", "Cháo lòng là món cháo được nấu theo phương thức nấu cháo thông thường, trong sự kết hợp với nước dùng ngọt làm từ xương lợn hay nước luộc lòng lợn, và nguyên liệu chính cho bát cháo không thể thiếu các món phủ tạng lợn luộc, dồi. Cháo lòng tương đối phổ thông thậm chí khá bình dân trong ẩm thực Việt Nam, được bán rộng rãi tại các cửa hàng lòng lợn trong cả nước, tạo nên một bộ ba sản phẩm được ăn theo thứ tự trong bữa ăn là tiết canh, lòng lợn, cháo lòng, và thường kết hợp với rượu đế.", "Cơm tấm (hay còn gọi là Cơm tấm Sài Gòn, hay cơm hộp) là một món ăn Việt Nam có nguyên liệu chủ yếu từ gạo tấm. Dù có nhiều tên gọi ở các vùng miền khác nhau, tuy nhiên nguyên liệu và cách thức chế biến của món ăn trên gần như là giống nhau. Một dĩa Cơm tấm thường được phục vụ kèm với một chén nước mắm và một chén canh, trên cùng dĩa ăn sẽ là một miếng sườn nướng và xung quanh là các món ăn mặn kèm khác cùng với mỡ hành được rưới lên trên cùng.", "Bánh đa cua, hay canh bánh đa, là một trong những món ăn phổ biến và đặc trưng trong ẩm thực Hải Phòng. Món bánh đa cua (bánh đa cua đồng hoặc bánh đa cua bể) kiểu Hải Phòng có thể xem là phiên bản phổ biến nhất của món canh bánh đa đỏ nói chung. Một số phiên bản ít phổ biến hơn của món canh bánh đa đỏ là lẩu cua đồng (có thể xem là một biến thể hiện đại của món bánh đa cua đồng) và canh bánh đa đỏ tôm sườn.", "Bánh mì chảo là món ăn được nhiều người yêu thích bởi sự kết hợp độc đáo giữa các nguyên liệu xúc xích, pa tê, trứng,… cùng với nước xốt", "Pizza là loại bánh dẹt,tròn được chế biến từ bột mì,nấm men... sau khi đã được ủ bột để nghỉ ít nhất 24 tiếng đồng hồ và nhào nặn thành loại bánh có hình dạng tròn và dẹt,và được cho vào lò nướng chín trước khi ăn. Mặc dù ngày nay pizza xuất hiện ở hầu hết các quốc gia trên thế giới, nhưng nó vẫn được xem là món ăn đặc trưng của ẩm thực Ý và đặc biệt là ở vùng Napoli (pizza napoletana). Trên thực tế, theo cảm nhận thông thường thì có thể nhận thấy các thành phần khác của pizza là sốt cà chua và pho mát mozzarella. Một trong những biến thể nổi tiếng nhất của pizza napoletana là pizza margherita."};
//        Food fd;
//        List <Food> lstFood = new ArrayList<>();
//        for (int i = 0; i < names.length; i++){
//            fd = new Food ();
//            fd.setFoodName(names[i]);
//            fd.setImage(pictures[i]);
//            fd.setPrice(prices[i]);
//            fd.setInfor(infors[i]);
//            fd.setDetails(details[i]);
//            lstFood.add(fd);
//        }
//        return lstFood;
//    }
}
