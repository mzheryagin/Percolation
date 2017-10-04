import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Created by mzheryagin on 03.10.2017.
 */
public class Percolation
{
    private int gridSize;
    private int openSites;
    private final static int virtTopSite = 0;
    private boolean[][] grid;
    private WeightedQuickUnionUF wquf;
    private Stopwatch sw;


    public Percolation(int n)                // create n-by-n grid, with all sites blocked
    {
        if(n<=0) throw new java.lang.IllegalArgumentException();
        gridSize = n;
        openSites = 0;
        grid = new boolean[gridSize][gridSize];
        wquf = new WeightedQuickUnionUF(gridSize*gridSize + 2);

        for(int i=0;i<n; i++)
        {
            for(int j=0;j<n;j++)
            {
                grid[i][j]= false;
            }
        }
    }

    /**
     * get UF array index
     */
    private int getUFIndex (int row, int col)
    {
        return 1+(row-1)*gridSize + (col-1);
    }

    public void open(int row, int col)    // open site (row, col) if it is not open already
    {
        if((row <1 && row > gridSize) || (col <1 && col > gridSize)) throw new java.lang.IllegalArgumentException();
        if(!isOpen(row,col))
        {
            grid[row-1][col-1] = true;
            if(row==1) wquf.union(getUFIndex(row, col),virtTopSite); //first row elements connected to virtTopSite


            openSites++;
        }
    }

    public boolean isOpen(int row, int col)  // is site (row, col) open?
    {
        if((row <1 && row > gridSize) || (col <1 && col > gridSize)) throw new java.lang.IllegalArgumentException();
        return grid[row-1][col-1];
    }

    public boolean isFull(int row, int col)  // is site (row, col) full?
    {
        if((row <1 && row > gridSize) || (col <1 && col > gridSize)) throw new java.lang.IllegalArgumentException();
        return wquf.connected(0,getUFIndex(row,col));
    }

    public int numberOfOpenSites()       // number of open sites
    {
        return openSites;
    }

    public boolean percolates()              // does the system percolate?
    {
        return wquf.connected(virtTopSite,gridSize*gridSize+1);
    }

    public static void main(String[] args)   // test client (optional)
    {

    }
}
