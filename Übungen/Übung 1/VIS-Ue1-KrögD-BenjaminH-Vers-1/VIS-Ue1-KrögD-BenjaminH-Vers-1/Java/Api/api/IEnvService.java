package at.fhooe.mc.api;

public interface IEnvService {
	public String[] requestEnvironmentDataTypes();
	public EnvData requestEnvironmentData(String _type);
	public EnvData[] requestAll ();
}
