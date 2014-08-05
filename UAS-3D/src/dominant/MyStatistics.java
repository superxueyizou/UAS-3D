package dominant;

import ec.EvolutionState;
import ec.simple.SimpleShortStatistics;

public class MyStatistics extends SimpleShortStatistics 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected static int[] accidents= new int[500];

	public MyStatistics() 
	{
		// TODO Auto-generated constructor stub
		super();
	}
	
	public void postEvaluationStatistics(final EvolutionState state)
	{
		super.postEvaluationStatistics(state);
		state.output.println(accidents[state.generation]+"", statisticslog);
	}

}
