package solutions.exercise8.crypto;

import java.util.Vector;

/**
 * Copyright by Darius Malysiak
 * This sourcecode may be used freely only for educational purposes.
 * */

public class EEA {
	
	/**
	 * Requires a ring with implemented div-operation and ring elements with implemented clone-method.
	 * Will return the a 2-tuple containing the GCD and the list of the EEA.
	 * Keep in mind that the ring element objects must be destroyed after their use, the EEA class does not
	 * own them.
	 * */
	public static EEAResult calculateGCD(int a, int b) 
	{
		EEAResult out = new EEAResult();
		
		//this list will contain every iterations result
		//each iteration will insert the tuple (a',b',q,s,t)
		out.m_history = new Vector<Integer>();
	
		boolean failed = true;
	
		int max_it = 500;
		int iteration_counter = 0;
	
		int a_ = 0;
		int b_ = 0;
	
		//this ensures that the larger element is always a
		if( a>b )//ricky approach
		{
			a_ = a;
			b_ = b;
		}
		else
		{
			b_ = a;
			a_ = b;
		}
	
		int temp1 = 0;
		int temp2 = 0;
	
		while(iteration_counter < max_it)
		{
			//get q
			int q = a_ / b_;
	
			//save a,b and q
			out.m_history.addElement(a_);
			out.m_history.addElement(b_);
			out.m_history.addElement(q);
			out.m_history.addElement(0);//placeholder for s
			out.m_history.addElement(0);//placeholder for t
	
			//get the new b
			temp1 = b_ * q;
			temp2 = a_ - temp1;
	
			//get a,b for next iteration
			a_ = b_;
			b_ = temp2;
	
			//check if end has been reached, i.e. check if b==0 (+)
			if( b_ == 0 )
			{
				out.m_history.addElement(a_);
				out.m_history.addElement(b_);
				out.m_history.addElement( 0 );
				out.m_history.addElement(0);//placeholder for s
				out.m_history.addElement(0);//placeholder for t
	
				failed = false;
				break;
			}
	
			++iteration_counter;
		}
	
		//if the maximal amount of iterations was exceeded
		if(failed == true)
		{
			out.m_history.clear();
			out.m_error = true;
	
			return out;
		}
	
		//otherwise continue
		int size =  out.m_history.size()/5;
		for(int i= size-1 ;i >= 0;--i)
		{
			//calculate s and t
			if(i==size-1)
			{
				out.m_history.set(i*5 + 3, 1);//s
				out.m_history.set(i*5 + 4, 0);//t
			}
			else
			{//printf("index %d | %d\n",(i+1)*5 + 4,size*5);
				out.m_history.set(i*5 + 3, (int)out.m_history.elementAt( (i+1)*5 + 4 ) );//s = last t
				temp1 = (int)out.m_history.elementAt(i*5 + 2) * (int)out.m_history.elementAt(i*5 + 3) ;//q*s
				out.m_history.set(i*5 + 4, (int)out.m_history.elementAt( (i+1)*5 + 3 ) - temp1 );//t = last s - q*s
			}
		}
		
		out.m_result = (int)out.m_history.elementAt(out.m_history.size()-1 -4);
	
		return out;
	}

	public static void printResult(EEAResult result)
	{
		System.out.println("|\t a \t\t b \t\t q \t\t s \t\t t \t |");
		for(int i=0;i<result.m_history.size()/5;++i)
		{
			System.out.println("|\t "+result.m_history.elementAt(i*5)+
					" \t\t "+result.m_history.elementAt(i*5+1)+
					" \t\t "+result.m_history.elementAt(i*5+2)+
					" \t\t "+result.m_history.elementAt(i*5+3)+
					" \t\t "+result.m_history.elementAt(i*5+4)+" \t |\n");
		}
	}
	
	
	public static EEAResult calculateInverse(int a, int b) 
	{
		EEAResult out = new EEAResult();
		
		//this list will contain every iterations result
		//each iteration will insert the tuple (a',b',q,s,t)
		out.m_history = new Vector<Integer>();
	
		boolean failed = true;
	
	
		int max_it = 500;
		int iteration_counter = 0;
	
		int a_ = 0;
		int b_ = 0;
	
		//this ensures that the larger element is always a
		if( a>b )//ricky approach
		{
			a_ = a;
			b_ = b;
		}
		else
		{
			b_ = a;
			a_ = b;
		}
	
		int temp1 = 0;
		int temp2 = 0;
	
		while(iteration_counter < max_it)
		{
			//get q
			int q = a_ / b_;
	
			//save a,b and q
			out.m_history.addElement(a_);
			out.m_history.addElement(b_);
			out.m_history.addElement(q);
			out.m_history.addElement(0);//placeholder for s
			out.m_history.addElement(0);//placeholder for t
	
			//get the new b
			temp1 = b_ * q;
			temp2 = a_ - temp1;
	
			//get a,b for next iteration
			a_ = b_;
			b_ = temp2;
	
			//check if end has been reached, i.e. check if b==0 (+)
			if( b_ == 0 )
			{
				out.m_history.addElement(a_);
				out.m_history.addElement(b_);
				out.m_history.addElement( 0 );
				out.m_history.addElement(0);//placeholder for s
				out.m_history.addElement(0);//placeholder for t
	
				failed = false;
				break;
			}
	
			++iteration_counter;
		}
	
		//if the maximal amount of iterations was exceeded
		if(failed == true)
		{
			out.m_history.clear();
			out.m_error = true;
	
			return out;
		}
	
		//otherwise continue
		int size =  out.m_history.size()/5;
		for(int i= size-1 ;i >= 0;i--)
		{
			//calculate s and t
			if(i==size-1)
			{
				out.m_history.set(i*5 + 3, 1);//s
				out.m_history.set(i*5 + 4, 0);//t
			}
			else
			{//printf("index %d | %d\n",(i+1)*5 + 4,size*5);
				out.m_history.set(i*5 + 3, (int)out.m_history.elementAt( (i+1)*5 + 4 ) );//s = last t
				temp1 = (int)out.m_history.elementAt(i*5 + 2) * (int)out.m_history.elementAt(i*5 + 3) ;//q*s
				out.m_history.set(i*5 + 4, (int)out.m_history.elementAt( (i+1)*5 + 3 ) - temp1 );//t = last s - q*s
			}
		}
		
		out.m_result = (int)out.m_history.elementAt(4);
	
		return out;
	}

}
