import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Created by mzheryagin on 03.10.2017.
 */
public class Percolation
{
    private int gridSize;
    private int openSites;
    private final static int virtTopSite = 0;
    private static int virtBottomSite;
    private boolean[][] grid;
    private WeightedQuickUnionUF wquf;
    private static final int[][] DIRECTIONS = {{1, 0}, {0, -1}, {-1, 0}, {0, 1}};


    public Percolation(int n)                // create n-by-n grid, with all sites blocked
    {
        if(n<=0) throw new java.lang.IllegalArgumentException();
        gridSize = n;
        openSites = 0;
        grid = new boolean[gridSize][gridSize];
        wquf = new WeightedQuickUnionUF(gridSize*gridSize + 2);
        virtBottomSite = gridSize*gridSize + 1;

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

    private boolean isValid(int row, int col)
    {
        return (row >=1 && row <= gridSize && col >=1 && col <= gridSize);
    }



    public void open(int row, int col)    // open site (row, col) if it is not open already
    {
        if(!isValid(row,col)) throw new java.lang.IllegalArgumentException();
        if(!isOpen(row,col))
        {
            grid[row-1][col-1] = true;
            openSites++;

            if(row==1) wquf.union(getUFIndex(row, col),virtTopSite); //first row elements connected to virtTopSite
            if(row==gridSize) wquf.union(getUFIndex(row, col),virtBottomSite); //and last row to virtBottomSite
            for (int[] r: DIRECTIONS )
            {
                if(isValid(row + r[0], col + r[1]) && isOpen(row + r[0], col + r[1]))
                {
                    wquf.union(getUFIndex(row, col),getUFIndex(row+r[0], col+r[1]));
                }
            }
        }
    }

    public boolean isOpen(int row, int col)  // is site (row, col) open?
    {
        if(!isValid(row,col)) throw new java.lang.IllegalArgumentException();
        return grid[row-1][col-1];
    }

    public boolean isFull(int row, int col)  // is site (row, col) full?
    {
        if(!isValid(row,col)) throw new java.lang.IllegalArgumentException();
        return wquf.connected(virtTopSite,getUFIndex(row,col));
    }

    public int numberOfOpenSites()       // number of open sites
    {
        return openSites;
    }

    public boolean percolates()              // does the system percolate?
    {
        return wquf.connected(virtTopSite,virtBottomSite);
    }

    public static void main(String[] args)   // test client (optional)
    {

    }
}
