package de.godlike.jremotepi.handlers;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.spi.resource.Singleton;

import de.godlike.jremotepi.persistence.entities.Device;

@Singleton
@Path("/device")
public class DeviceHandler {
	@SuppressWarnings("unchecked")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Device> listAllDevices() {
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("jRemotePi");
		EntityManager em = factory.createEntityManager();
		try {
			Query q = em.createQuery("select d from Device d");
			return q.getResultList();
		} finally {
			em.close();
		}
	}

	@GET
	@Path("/{systemId}/{deviceId}/{state}")
	public void switchDevice(@PathParam("systemId") String systemId,
			@PathParam("deviceId") String deviceId,
			@PathParam("state") String state) {
		ProcessBuilder sendProcessBuilder = new ProcessBuilder(
				"/usr/sbin/send", systemId, deviceId, state);
		try {
			sendProcessBuilder.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@GET
	@Path("/{entityId}/{state}")
	public void switchDevice(@PathParam("entityId") String entityId,
			@PathParam("state") String state) {
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("jRemotePi");
		EntityManager em = factory.createEntityManager();
		try {
			Device deviceToSwitch = em.find(Device.class, entityId);
			this.switchDevice(deviceToSwitch.getSystemId(),
					deviceToSwitch.getDeviceId(), state);
		} finally {
			em.close();
		}
	}

	@PUT
	@Path("/{deviceName}/{systemId}/{deviceId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Device putDevice(@PathParam("deviceName") String deviceName,
			@PathParam("systemId") String systemId,
			@PathParam("deviceId") String deviceId) {
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("jRemotePi");
		EntityManager em = factory.createEntityManager();
		try {
			em.getTransaction().begin();
			Device newDevice = new Device();
			newDevice.setName(deviceName);
			newDevice.setSystemId(systemId);
			newDevice.setDeviceId(deviceId);
			Device mergedDevice = em.merge(newDevice);
			em.getTransaction().commit();

			return em.find(Device.class, mergedDevice.getId());
		} finally {
			em.close();
		}
	}

	@DELETE
	@Path("/{deviceId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Device> deleteDevice(@PathParam("deviceId") Long deviceId) {
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("jRemotePi");
		EntityManager em = factory.createEntityManager();
		try {
			em.getTransaction().begin();
			em.remove(em.find(Device.class, deviceId));
			em.getTransaction().commit();

			return this.listAllDevices();
		} finally {
			em.close();
		}
	}
}
