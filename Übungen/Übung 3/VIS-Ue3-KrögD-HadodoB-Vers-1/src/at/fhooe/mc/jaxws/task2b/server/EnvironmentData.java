package at.fhooe.mc.jaxws.task2b.server;


import java.rmi.RemoteException;

import javax.jws.*;

import at.fhooe.mc.jaxws.task2b.service.EnvData;
import at.fhooe.mc.jaxws.task2b.service.IEnvService;

@WebService(endpointInterface="at.fhooe.mc.jaxws.service.IEnvService")
public class EnvironmentData implements IEnvService{

	@Override
	public String[] requestEnvironmentDataTypes() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EnvData requestEnvironmentData(String _type) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EnvData[] requestAll() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}



}
