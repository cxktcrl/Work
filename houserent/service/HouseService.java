package houserent.service;

import houserent.domain.House;

public class HouseService {
    private House[] houses;
    private int houseNums = 1;
    private int idCounter = 1;

    public HouseService(int size){
        houses = new House[size];//创建HouseService对象，指定数组大小
        houses[0] = new House(1,"赵波","112","商都",2000,"已出租");
    }

    public House findByid(int findId){
        for (int i=0;i<houseNums;i++){
            if (findId == houses[i].getId()){
                return  houses[i];
            }
        }
        return null;
    }

    //dle方法，删除一个房屋信息
    public boolean del(int delId){
         int index = -1;
         for(int i=0;i<houseNums;i++){
             if (delId == houses[i].getId()){
                 index = 1;
             }
         }
         if (index == -1){
             return false;
         }
        for (int i=index;i<houseNums-1;i++){
            houses[i] = houses[i+1];
        }
        houses[--houseNums] = null;
        return true;
    }


    //add方法，添加新对象
    public boolean add(House newHouse){
        if (houseNums >= houses.length){
            System.out.println("数组已满！不能再添加！");
            return false;
        }
        houses[houseNums++] = newHouse;
        //houseNums ++ ;
        //idCounter ++ ;
        newHouse.setId(++idCounter);
        return true;
    }

    public House[] list(){
        return houses;
    }
}
