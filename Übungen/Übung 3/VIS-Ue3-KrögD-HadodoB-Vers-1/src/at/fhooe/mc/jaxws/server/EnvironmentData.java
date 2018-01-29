package at.fhooe.mc.jaxws.server;


import java.rmi.RemoteException;

import javax.jws.*;

import at.fhooe.mc.jaxws.service.EnvData;
import at.fhooe.mc.jaxws.service.IEnvService;

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
