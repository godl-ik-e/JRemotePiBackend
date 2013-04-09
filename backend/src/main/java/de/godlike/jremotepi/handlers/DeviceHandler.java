package de.godlike.jremotepi.handlers;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.godlike.jremotepi.persistence.entities.Device;

@Path("/device")
public class DeviceHandler {

	private void switchDevice(String systemId, String deviceId, String state) {
		ProcessBuilder sendProcessBuilder = new ProcessBuilder(
				"/usr/sbin/send", systemId, deviceId, state);
		try {
			sendProcessBuilder.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

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
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Device getDevice(@PathParam("id") String id) {
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("jRemotePi");
		EntityManager em = factory.createEntityManager();
		try {
			return em.find(Device.class, Long.parseLong(id));
		} finally {
			em.close();
		}
	}

	@GET
	@Path("/{id}/{state}")
	public void switchDevice(@PathParam("id") Long id,
			@PathParam("state") String state) {
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("jRemotePi");
		EntityManager em = factory.createEntityManager();
		try {
			Device deviceToSwitch = em.find(Device.class, id);
			this.switchDevice(deviceToSwitch.getSystemId(),
					deviceToSwitch.getDeviceId(), state);
		} finally {
			em.close();
		}
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Device putDevice(@FormParam("deviceName") String deviceName,
			@FormParam("systemId") String systemId,
			@FormParam("deviceId") String deviceId) {
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

	@POST
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Device updateDevice(@PathParam("id") Long id,
			@FormParam("deviceName") String deviceName,
			@FormParam("systemId") String systemId,
			@FormParam("deviceId") String deviceId) {
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("jRemotePi");
		EntityManager em = factory.createEntityManager();
		try {
			em.getTransaction().begin();
			Device device2Update = em.find(Device.class, id);
			device2Update.setName(deviceName);
			device2Update.setSystemId(systemId);
			device2Update.setDeviceId(deviceId);
			Device mergedDevice = em.merge(device2Update);
			em.getTransaction().commit();

			return em.find(Device.class, mergedDevice.getId());
		} finally {
			em.close();
		}
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Device> deleteDevice(@PathParam("id") Long id) {
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("jRemotePi");
		EntityManager em = factory.createEntityManager();
		try {
			em.getTransaction().begin();
			em.remove(em.find(Device.class, id));
			em.getTransaction().commit();

			return this.listAllDevices();
		} finally {
			em.close();
		}
	}
}
