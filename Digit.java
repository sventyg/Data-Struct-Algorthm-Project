
package cps350_project;

class Digit
{
  int digit;    // data field
  Digit previous;
  Digit next;

  // put your code for constructors and methods
  //contructor for creating an empty digit and for previous and next to be null
  Digit()
  {
      digit = 0;
      previous = null;
      next = null;
  }
  
  //initizalize digit with the integer value
  Digit(final int value)
  {
      digit = value;
      previous = null;
      next = null;
  }
  
  //digit copy constructor 
  Digit(Digit number)
  {
      digit = number.digit;
      previous = number.previous;
      next = number.next;  
  }
}
