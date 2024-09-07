
package cps350_project;


class BigNumber
{
  Digit head;  // reference to the head node
  Digit tail;  // reference to the tail node

  public BigNumber(final String strNum)
  {

//      Make an array to go through the string
    for(int i =0; i < strNum.length(); i++){
        //View each character and save it to a variable
        char num = strNum.charAt(i);
        //check to see if the first character is a minus sign
        if (num == '-')
        {
            //if so, make the head node 1
            Digit newDigit = new Digit(1);
            //point the head and tail reference to the newDigit
            head = tail= newDigit; 
        } else{
        //Change the char to an int value
        int number = num - '0';
        //insert that value into a digit
        Digit newDigit = new Digit(number);
        //check to see if head Digit is empty, it should be unless there was a minus sign
        if(head == null){
           //if there wasn't a minus sign then make the head node 0
           //do this by making a temp Digit to hold the 0 and then shuffle the head reference pointer
           Digit temp = new Digit();
           head = temp;
           head.next = tail;
        }
        if (tail == null){
            tail = newDigit;
            head.next = tail;
            tail.previous = head;
        } else{
        //inserting the digits from the back of the doubly linked list
        //so always update the tail reference pointer
        newDigit.previous = tail;
        newDigit.next = null;
        tail.next = newDigit;
        tail = newDigit;
        }
        }
    }
    
  }

  // copy constructor, notice that you need to create a new list which represents
  // the same number as num
 public BigNumber(final BigNumber num)
  {

    head = new Digit(num.head);
    tail = head;
    Digit iter = num.head;
    while (iter.next != null) {
        Digit temp = new Digit (iter.next);
        temp.previous = tail;
        temp.next = null;
        tail.next = temp;
        tail = temp;
        iter = iter.next;
    }
  }

  // addition assignment: adding the given number to the current number, +=
  public void add_assign(final BigNumber b)
  {
    if(head.digit == 0 && b.head.digit == 1)
    {
        sub_assign(b);
        return;
    }
    int carry= 0;
    int sum;
    Digit iter = tail;
    Digit iter2 = b.tail;
    while(iter != head || iter2 != b.head)
    {
        int aDigit;
        int bDigit;
        if(iter != head && iter2 != b.head)
        {
            aDigit = iter.digit;
            bDigit = iter2.digit;
            sum = (aDigit + bDigit + carry) % 10;
            carry = (aDigit + bDigit + carry) /10;
            iter.digit = sum;
            iter = iter.previous;
            iter2 = iter2.previous;

        } else if(iter != head && iter2 == b.head){
            aDigit = iter.digit;
            bDigit = 0; 
            sum = (aDigit + bDigit + carry) % 10;
            carry = (aDigit + bDigit + carry) /10;
            iter.digit = sum;
            iter = iter.previous;
            
        }else if(iter2 != b.head && iter == head)
        {
            aDigit = 0;
            bDigit = iter2.digit;
            sum = (aDigit + bDigit + carry) % 10;
            carry = (aDigit + bDigit + carry) /10;
            Digit newDigit = new Digit(sum);
            newDigit.next = head;
            head.previous = newDigit;
            head = newDigit;
            iter2 = iter2.previous; 
        }     
  }
    if(carry>0){
        Digit newDigit = new Digit(carry);
        newDigit.next = head.next;
        head.next.previous = newDigit;
        newDigit.previous = head;
        head.next = newDigit;
        }
  }
  
  void print()
    {
        for(Digit iter = head; iter!=null; iter=iter.next)
        {
            System.out.print(iter.digit+ " ");
        }
        System.out.println();
    }
  
  // addition: return a + b
  public static BigNumber add(final BigNumber a, final BigNumber b)
  {
    BigNumber c = new BigNumber(a);
    c.add_assign(b);  // calling the method for +=
    return c;
  }

//subtract
  public static BigNumber sub(final BigNumber a, final BigNumber b)
  {
    BigNumber c = new BigNumber(a);
    c.sub_assign(b);    // calling the method for -=
    return c;
  }
  
  public void sub_assign(final BigNumber b)
  {
    if(head.digit == 1 && b.head.digit == 1)
    {
    add_assign(b);
    return;
    }
    int neg = 0;
    int borrow= 0;
    int sub;
    Digit iter = tail;
    Digit iter2 = b.tail;
    while(iter != null || iter2 != null)
    {
        System.out.println("yup");
        print();
        int aDigit;
        int bDigit;
        if(iter != null && iter2 != null)
        {
            System.out.println("yup2");
            aDigit = iter.digit;
            bDigit = iter2.digit;
            if (aDigit < bDigit){ // first number smaller so need to borrow
                if (iter.previous == null && iter2.previous == null) //final number is negative
                {
                    neg = 1;
                   
                }               
                sub = (aDigit + 10 - bDigit);
                borrow = 1;
                iter.digit = sub;
                iter = iter.previous;
                iter2 = iter2.previous;
            }
            else{
                System.out.println("yup2else");
                print();
                sub = (aDigit - borrow - bDigit);
                borrow = 0;
                iter.digit = sub;
                print();
                iter = iter.previous;
                iter2 = iter2.previous;  
                
            }
        } 
        else if(iter != null && iter2 == null){
            System.out.println("yup3");
            aDigit = iter.digit;
            bDigit = 0;
            sub = aDigit - bDigit - borrow;
            borrow = 0;
            iter.digit = sub;
            iter = iter.previous;
        }
        else if(iter2 != null && iter == null)
        {
            System.out.println("yup4");
            bDigit = iter2.digit;
            sub = bDigit;
            borrow = 0;
            neg = 1;
            if(iter2.previous != null)
            {
            Digit newDigit = new Digit(sub);
            newDigit.next = head;
            head.previous = newDigit;
            head = newDigit;
            iter2 = iter2.previous;
            } else
                break;
        }
        
  }
    if(neg>0){
        System.out.println("yup5");
        Digit newDigit = new Digit(neg);
        newDigit.next = head;
        head.previous = newDigit;
        head = newDigit;
        }
  }


  public void multiply_assign(final BigNumber b)
  {
    if((head.digit == 0) && (b.head.digit == 1))
        head.digit = 1;
    if((head.digit == 1) && (b.head.digit == 1))
        head.digit = 0;
   BigNumber partialP = new BigNumber("0");
   Digit sol = partialP.tail;
   
   int carry = 0;
   int product = 0;
   int num = 0;
   for(Digit iter =tail; iter != head; iter = iter.previous){
       System.out.println("outer loop: ");
       String partial = "";
       int aDigit = iter.digit;
       for(Digit iter2 = b.tail; iter2 != b.head; iter2 = iter2.previous){
           System.out.println("inner loop:");
           int bDigit = iter2.digit;
           product += (aDigit * bDigit + carry);
           carry = product /10;
           num = product%10;
           sol.digit = num; //add to new
           sol = sol.previous; //move to new spot
           partial += product;
           System.out.println(partial);
           BigNumber temp = new BigNumber(partial);
            System.out.println("This is temp");
            temp.print();
            System.out.println("This is partialP before add");
           partialP.print();
           partialP.add_assign(temp);  
           System.out.println("This is partialP after add");
           
           partialP.print();
       }
   }
    if(carry >0)
        {
        System.out.println("works4");
        Digit newDigit = new Digit(carry);
        newDigit.next = head.next;
        head.next.previous = newDigit;
        newDigit.previous = head;
        head.next = newDigit;
        }
  }
//    int num1 = 0, num2 = 0;  int carry = 0;
//    int pos = 0;
//    //checks if number will be positive or negative
//    if((head.digit == 0) && (b.head.digit == 1) || (head.digit == 1) && (b.head.digit == 0))
//        pos = 1;
//    if((head.digit == 1) && (b.head.digit == 1))
//        pos = 0;
//    //New number
//    BigNumber result = new BigNumber("0");
//    Digit mult = result.tail;
//    
//    //multiplying number
//    Digit second = b.tail;
//    //Starts at tail  
//    for(Digit iter =tail; iter != head; iter = iter.previous){
//        int m = iter.digit*second.digit;
//        if (m > 10){
//             mult.digit = 0;
//             carry = 
//        } else
//        {
//            
//        }
//        carry = m%10
//    }

    
    // Generates numbers 
//    Digit firstnum = head.next;
//    Digit secondnum = b.head.next;
//    
//    while (firstnum != null || secondnum != null)  
//    {  
//        if (firstnum != null)  
//        {  
//            num1 = num1*10 + firstnum.digit;  
//            firstnum = firstnum.next;  
//        }  
//        if (secondnum != null)  
//        {  
//            num2 = num2*10 + secondnum.digit;  
//            secondnum = secondnum.next; 
//        }
//    }
//    //applies negative
//    if (pos == 1)
//    {
//        num1 = num1*(-1);
//    }
//    return num1*num2; 

 
  
  
  public void div_assign(final BigNumber b)
  {
    int num1 = 0, num2 = 0;  
    int pos = 0;
    //checks if number will be positive or negative
    if((head.digit == 0) && (b.head.digit == 1) || (head.digit == 1) && (b.head.digit == 0))
        pos = 1;
    if((head.digit == 1) && (b.head.digit == 1))
        pos = 0;      
//      if (b.head.next == null && b.head.digit == 0)
//      {
//        Digit newDigit = new Digit(0);
//        newDigit.next = head;
//        head.previous = newDigit;
//        head = newDigit;
//      } else
//      {
//          //making divinding number
//          String str = "0";
//          BigNumber div = new BigNumber(str);
//          div.add_assign(b);
//          BigNumber result = new BigNumber();
//          //turn Bignumber into number
//          for ()
//          {
//              
//          }
//          int divider = 10;//temporary divider
//          Digit iter = head;
//          int num = 0;
//          int remainder = 0;
//          while (iter != null)
//          {
//              System.out.print("test1");//test   
//              result.head.digit = iter.digit/divider;//divides
//              remainder = remainder + iter.digit%divider; //remainder
//              if (iter.previous != null)
//              {
//                  
//              }
//              
//              num = num + iter.digit/divider;
//          }
//          
//      }
//              
//      
//      
//  }
//  
//   public String toString(){
//      //create an empty string
//      String result = "";
//      //set the Digit current to the head node in order to go through all the digits
//      Digit current = head;
//      //check to see if the head was negative or positive
//      if(head.digit == 1)
//      {
//          //if negative add a minus sign to the string and then move current
//          result += "-";
//          current = current.next;
//      }
//      if(head.digit == 0)
//          //if positive just move current
//          current = current.next;
//      //while loop to add the rest of the elements to the string
//      while(current != null){
//          result += current.digit;
//          current = current.next;
//      }
//      //return the string which will hold the number that the digits make up
//      return result;
  }
  
}





