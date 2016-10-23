

public class AssertDemo
{
    static int val=3;      /** val значение которое будит уменьшаться*/
    static int getnum() {
        return val--;
    }

    public static void main(String[] args)
    {
        int n;
        for(int i=0; i<10; i++){
            n=getnum();

            System.out.println("n="+n);
        }
    }
}
