package solutions.exercise8.crypto;

/**
 * Copyright by Darius Malysiak
 * This sourcecode may be used freely only for educational purposes.
 * */

public class Matrix2Dint {

	public Matrix2Dint(int rows, int columns)
	{
		m_rows = rows;
		m_columns = columns;
		
		m_data = new int[rows*columns];
		
		for(int i=0;i<m_rows*m_columns;++i)
		{
			m_data[i]=0;
		}
	}
	
	/**
	 * Prints the matrix to the console
	 * */
	public void print()
	{
		for(int i=0;i<m_rows;++i)
		{
			for(int j=0;j<m_columns;++j)
			{
				System.out.print(""+ m_data[i*m_columns + j] +" ");
			}	
			System.out.println("");
		}
	}
	
	/**
	 * Returns the data from row i, column j
	 * */
	public int getData(int i, int j)
	{
		if(i>=m_rows)
		{
			System.out.println("ERROR: row index out of bounds");
			return 0;
		}
		
		if(j>=m_columns)
		{
			System.out.println("ERROR: column index out of bounds");
			return 0;
		}
		
		return m_data[i*m_columns + j];
	}
	
	/**
	 * Inserts the given data into row i, column j
	 * */
	public void setData(int i, int j, int data)
	{
		if(i>=m_rows)
		{
			System.out.println("ERROR: row index out of bounds");
			return;
		}
		
		if(j>=m_columns)
		{
			System.out.println("ERROR: column index out of bounds");
			return;
		}
		
		m_data[i*m_columns + j] = data;
	}
	
	/**
	 * Returns a reference to the linear array which contains the actual 
	 * matrix data. Keep in mind that the matrix is stored in row 
	 * major format.
	 * */
	public int[] getData()
	{
		return m_data;
	}
	
	public int getRowCount()
	{
		return this.m_rows;
	}
	
	public int getColumnCount()
	{
		return this.m_columns;
	}
	
	public int determinant()
	{
		int det = determinant_(this);
		
		return det;
	}
	
	/**
	 * Reduces entries with modulus m
	 * */
	public void mod(int m)
	{
		for(int i=0;i<m_rows;++i)
		{
			for(int j=0;j<m_columns;++j)
			{
				this.setData(i, j, Toolbox.mod_(this.getData(i, j),m));
			}	
		}
	}
	
	/**
	 * Scales entries with a
	 * */
	public void scale(int a)
	{
		for(int i=0;i<m_rows;++i)
		{
			for(int j=0;j<m_columns;++j)
			{
				this.setData(i, j, this.getData(i, j)*a);
			}	
		}
	}
	
	public Matrix2Dint invertMod(int m)
	{
		Matrix2Dint inv = new Matrix2Dint(this.m_rows,this.m_columns);
		
		if(this.m_rows != this.m_columns)
		{
			System.out.println("ERROR: matrix must be square");
			return inv;
		}
		
		switch(this.m_rows)
		{
		case 2:
			
			break;
			
		case 3:
		{
			int det = Toolbox.mod_(this.determinant(),m);
			//System.out.println("det  "+det+" inv "+EEA.calculateInverse(det, m).m_result);
			det = Toolbox.mod_( EEA.calculateInverse(det, m).m_result,m);
			//System.out.println("det mod "+det+" "+m);
			
			int A = (this.getData(1, 1)*this.getData(2, 2) - this.getData(1, 2)*this.getData(2, 1))*det;
			int B = -(this.getData(1, 0)*this.getData(2, 2) - this.getData(1, 2)*this.getData(2, 0))*det;
			int C = (this.getData(1, 0)*this.getData(2, 1) - this.getData(1, 1)*this.getData(2, 0))*det;
			int D = -(this.getData(0, 1)*this.getData(2, 2) - this.getData(0, 2)*this.getData(2, 1))*det;
			int E = (this.getData(0, 0)*this.getData(2, 2) - this.getData(0, 2)*this.getData(2, 0))*det;
			int F = -(this.getData(0, 0)*this.getData(2, 1) - this.getData(0, 1)*this.getData(2, 0))*det;
			int G = (this.getData(0, 1)*this.getData(1, 2) - this.getData(0, 2)*this.getData(1, 1))*det;
			int H = -(this.getData(0, 0)*this.getData(1, 2) - this.getData(0, 2)*this.getData(1, 0))*det;
			int I = (this.getData(0, 0)*this.getData(1, 1) - this.getData(0, 1)*this.getData(1, 0))*det;
			
			inv.setData(0, 0, Toolbox.mod_(A,m));
			inv.setData(0, 1, Toolbox.mod_(D,m));
			inv.setData(0, 2, Toolbox.mod_(G,m));
			inv.setData(1, 0, Toolbox.mod_(B,m));
			inv.setData(1, 1, Toolbox.mod_(E,m));
			inv.setData(1, 2, Toolbox.mod_(H,m));
			inv.setData(2, 0, Toolbox.mod_(C,m));
			inv.setData(2, 1, Toolbox.mod_(F,m));
			inv.setData(2, 2, Toolbox.mod_(I,m));
		}
			//inv.print();
			//System.out.println("njahhh"+m+" "+det+" "+A+" "+B+" "+C+" "+D+" "+E+" "+F+" "+G+" "+H+" "+I);
			
			break;
			
		default:
			
			//calculate the adjugate matrix
			int det = Toolbox.mod_(this.determinant(),m);
			//System.out.println("det  "+det+" inv "+EEA.calculateInverse(det, m).m_result);
			det = Toolbox.mod_( EEA.calculateInverse(det, m).m_result,m);
			
			for(int i=0;i<m_rows;++i)
			{
				for(int j=0;j<m_columns;++j)
				{
					Matrix2Dint minor_ij = this.removeRowColumn(i, j);
					int det_minor = Toolbox.mod_(minor_ij.determinant(),m);
					inv.setData(j, i, Toolbox.mod_( (int)( Math.pow(-1, i+1+j+1)*(det_minor*det) ), m ));//implicit transpose, thus inverse
				}	
			}
			
			
			break;
		}
		
		return inv;
	}
	
	public Matrix2Dint invert()
	{
		Matrix2Dint inv = new Matrix2Dint(this.m_rows,this.m_columns);
		
		if(this.m_rows != this.m_columns)
		{
			System.out.println("ERROR: matrix must be square");
			return inv;
		}
		
		switch(this.m_rows)
		{
		case 2:
			
			break;
			
		case 3:
		{
			double det = this.determinant();
			
			double A = (double)(this.getData(1, 1)*this.getData(2, 2) - this.getData(1, 2)*this.getData(2, 1))/det;
			double B = -(double)(this.getData(1, 0)*this.getData(2, 2) - this.getData(1, 2)*this.getData(2, 0))/det;
			double C = (double)(this.getData(1, 0)*this.getData(2, 1) - this.getData(1, 1)*this.getData(2, 0))/det;
			double D = -(double)(this.getData(0, 1)*this.getData(2, 2) - this.getData(0, 2)*this.getData(2, 1))/det;
			double E = (double)(this.getData(0, 0)*this.getData(2, 2) - this.getData(0, 2)*this.getData(2, 0))/det;
			double F = -(double)(this.getData(0, 0)*this.getData(2, 1) - this.getData(0, 1)*this.getData(2, 0))/det;
			double G = (double)(this.getData(0, 1)*this.getData(1, 2) - this.getData(0, 2)*this.getData(1, 1))/det;
			double H = -(double)(this.getData(0, 0)*this.getData(1, 2) - this.getData(0, 2)*this.getData(1, 0))/det;
			double I = (double)(this.getData(0, 0)*this.getData(1, 1) - this.getData(0, 1)*this.getData(1, 0))/det;
			
			//System.out.println("njahhh"+det+" "+A+" "+B+" "+C+" "+D+" "+E+" "+F+" "+G+" "+H+" "+I);
			
			inv.setData(0, 0, (int)A);
			inv.setData(0, 1, (int)D);
			inv.setData(0, 2, (int)G);
			inv.setData(1, 0, (int)B);
			inv.setData(1, 1, (int)E);
			inv.setData(1, 2, (int)H);
			inv.setData(2, 0, (int)C);
			inv.setData(2, 1, (int)F);
			inv.setData(2, 2, (int)I);
		}	
			break;
			
		default:
			//calculate the adjugate matrix
			double det = this.determinant();
			
			for(int i=0;i<m_rows;++i)
			{
				for(int j=0;j<m_columns;++j)
				{
					Matrix2Dint minor_ij = this.removeRowColumn(i, j);
					double det_minor = minor_ij.determinant();
					inv.setData(j, i, (int)( Math.pow(-1, i+1+j+1)*(det_minor/det) ) );//implicit transpose, thus inverse
				}	
			}
			
			break;
		}
		
		return inv;
	}
	
	/**
	 * Returns this*array, with array being interpreted as a column vector. Thus the output matrix will 
	 * have array.length rows and 1 column.
	 * */
	public Matrix2Dint multiplyMatVec1(int[] array)
	{
		Matrix2Dint out = new Matrix2Dint(array.length,1);
		
		if(array.length != m_columns)
		{
			System.out.println("ERROR: matrix and array dimensions must agree!");
			return out;
		}
		
		int sum = 0;
		
		for(int i=0;i<m_rows;++i)
		{
			for(int j=0;j<m_columns;++j)
			{
				sum += this.getData(i, j)*array[j];
			}	
			out.setData(i, 0, sum);
			sum = 0;
		}
		
		return out;
	}
	
	/**
	 * Returns this*array, with array being interpreted as a column vector. Thus the output matrix will 
	 * have array.length rows and 1 column.
	 * */
	public int[] multiplyMatVec2(int[] array)
	{
		int[] out = new int[array.length];
		
		if(array.length != m_columns)
		{
			System.out.println("ERROR: matrix and array dimensions must agree!");
			return out;
		}
		
		int sum = 0;
		
		for(int i=0;i<m_rows;++i)
		{
			for(int j=0;j<m_columns;++j)
			{
				sum += this.getData(i, j)*array[j];
			}	
			out[i] = sum;
			sum = 0;
		}
		
		return out;
	}
	
	/**
	 * Returns this*array, with array being interpreted as a column vector. Thus the output matrix will 
	 * have array.length rows and 1 column.
	 * */
	public Matrix2Dint multiplyMatVec1Mod(int[] array, int m)
	{
		Matrix2Dint out = new Matrix2Dint(array.length,1);
		
		if(array.length != m_columns)
		{
			System.out.println("ERROR: matrix and array dimensions must agree!");
			return out;
		}
		
		int sum = 0;
		
		for(int i=0;i<m_rows;++i)
		{
			for(int j=0;j<m_columns;++j)
			{
				sum += this.getData(i, j)*array[j];
			}	
			out.setData(i, 0, Toolbox.mod_(sum,m));
			sum = 0;
		}
		
		return out;
	}
	
	/**
	 * Returns this*array, with array being interpreted as a column vector. Thus the output matrix will 
	 * have array.length rows and 1 column.
	 * */
	public int[] multiplyMatVec2Mod(int[] array, int m)
	{
		int[] out = new int[array.length];
		
		if(array.length != m_columns)
		{
			System.out.println("ERROR: matrix and array dimensions must agree!");
			return out;
		}
		
		int sum = 0;
		
		for(int i=0;i<m_rows;++i)
		{
			for(int j=0;j<m_columns;++j)
			{
				sum += this.getData(i, j)*array[j];
			}	
			out[i] = Toolbox.mod_(sum,m);
			sum = 0;
		}
		
		return out;
	}
	
	public Matrix2Dint getSubMatrix(int startRow, int startColumn, int rows, int columns)
	{
		Matrix2Dint out = new Matrix2Dint(rows,columns);
		
		for(int i=0;i<rows;++i)
		{
			for(int j=0;j<columns;++j)
			{
				out.setData(i, j, this.getData(startRow+i, startColumn+j));
			}	
		}
		
		return out;
	}
	
	/**
	 * returns a submatrix by removing row 'row' and column 'column' from the current matrix.
	 * */
	public Matrix2Dint removeRowColumn(int row, int column)
	{
		//finds determinant using row-by-row expansion
	      Matrix2Dint submat = new Matrix2Dint(this.getRowCount()-1, this.getColumnCount()-1);
	      int v_row = 0;
	      int v_column = 0;
	      
	      for(int a=0; a < this.getRowCount(); a++)
	      {

			if(a==row)
			{	
				v_row = -1;
				continue;
			}
	    	  
	        for(int b=0; b <this.getColumnCount(); b++)
	        {
	        	
				if(b==column)
				{	
					v_column = -1;
					continue;
				}
				
	    		submat.setData(a+v_row, b+v_column, this.getData(a, b));
	          
	        }
	        
			v_column = 0;
	        
	      }
	      
	      return submat;
	}
	
	private int determinant_(Matrix2Dint mat)
	{ 
	    
		int sum=0; 
	    int s;
	    
	    if(mat.getColumnCount()==1 && mat.getRowCount()==1)
	    {  //bottom case of recursion. size 1 matrix determinant is itself.
	      return(mat.getData(0, 0));
	    }
	    
	    for(int i=0;i<mat.getRowCount();i++)
	    { 
	      //finds determinant using row-by-row expansion
	      Matrix2Dint submat = new Matrix2Dint(mat.getRowCount()-1, mat.getColumnCount()-1);
	      boolean hit = false;
	    	
	      for(int a=0; a < mat.getRowCount(); a++)
	      {
	        for(int b=1; b <mat.getColumnCount(); b++)
	        {
	        	
	          if(a!=i)
	          {		
	        	  //System.out.println("asdasd "+a+" "+b+" "+submat.getRowCount()+" "+submat.getColumnCount());
	        	  
	          		if(hit != true)
	          		{
	          			submat.setData(a, b-1, mat.getData(a, b));
	          		}
	          		else
	          		{
	          			submat.setData(a-1, b-1, mat.getData(a, b));
	          		}
	          		
	          		//System.out.println("asdasd2 "+a+" "+b+" "+submat.getRowCount()+" "+submat.getColumnCount());
	          }
	          else//just exit the loop for the pivot row
	          {
	        	  hit = true;
	        	  continue;
	          }
	          
	        }
	      }
	      
	      if(i%2==0)
	      {
	        s=1;
	      }
	      else
	      {
	        s=-1;
	      }
	      
	      //System.out.print("submat ");submat.print();
	      
	      sum += s * mat.getData(i, 0)*(determinant_(submat)); 
	      
	     //System.out.print("subsum "+sum);
	    }
	    
	    //System.out.println("sum "+sum);
	    return(sum); //returns determinant value. once stack is finished, returns final determinant.
	  }
	
	private int[] m_data;
	private int m_rows;
	private int m_columns;
	
}
