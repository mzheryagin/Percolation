import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;


/**
 * Created by mzheryagin on 03.10.2017.
 */
public class PercolationStats
{
    private double[] percolationThreshold;

    public PercolationStats(int n, int trials)    // perform trials independent experiments on an n-by-n grid
    {
        if(n<=0 && trials<=0) throw new java.lang.IllegalArgumentException();
        percolationThreshold = new double[trials];
        for(int i=0; i<trials;i++)
        {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates())
            {
                int row = StdRandom.uniform(1,n+1);
                int col = StdRandom.uniform(1,n+1);
                percolation.open(row,col);
            }
            percolationThreshold[i] = (double)percolation.numberOfOpenSites() / (n*n);
        }
    }

    public double mean()                          // sample mean of percolation threshold
    {
        return StdStats.mean(percolationThreshold);
    }

    public double stddev()                        // sample standard deviation of percolation threshold
    {
        return StdStats.stddev(percolationThreshold);
    }

    public double confidenceLo()                  // low  endpoint of 95% confidence interval
    {
        return mean() - 1.96*stddev()/Math.sqrt(percolationThreshold.length);
    }

    public double confidenceHi()                  // high endpoint of 95% confidence interval
    {
        return mean() + 1.96*stddev()/Math.sqrt(percolationThreshold.length);
    }

    public static void main(String[] args)        // test client (described below)
    {
        Stopwatch sw = new Stopwatch();
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats percStats = new PercolationStats(n, trials);

        StdOut.println("mean \t\t\t\t\t= "+percStats.mean());
        StdOut.println("stddev \t\t\t\t\t= "+percStats.stddev());
        StdOut.println("95% confidence interval = "+ percStats.confidenceLo() + ", " + percStats.confidenceHi());
    }
}
