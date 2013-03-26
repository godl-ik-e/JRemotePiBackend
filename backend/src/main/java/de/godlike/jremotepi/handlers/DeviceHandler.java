package de.godlike.jremotepi.handlers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriBuilderException;
import javax.ws.rs.core.UriInfo;

import de.godlike.jremotepi.business.dtos.Body;
import de.godlike.jremotepi.business.dtos.Button;
import de.godlike.jremotepi.business.dtos.DeviceOverview;
import de.godlike.jremotepi.business.dtos.Element;
import de.godlike.jremotepi.business.dtos.Footer;
import de.godlike.jremotepi.business.dtos.NavBar;
import de.godlike.jremotepi.business.dtos.OverElement;
import de.godlike.jremotepi.business.dtos.Text;
import de.godlike.jremotepi.persistence.entities.Device;

@Path("/device")
public class DeviceHandler {
	@Context
	UriInfo uriInfo;

	@SuppressWarnings("unchecked")
	private DeviceOverview getDevicesOverview() {
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("jRemotePi");
		EntityManager em = factory.createEntityManager();
		try {
			Query q = em.createQuery("select d from Device d");
			List<Device> allDevices = q.getResultList();

			List<Button> navbarButtons = new ArrayList<Button>();
			navbarButtons.add(new Button("Geräte", "uri..", "NEWPAGE"));
			navbarButtons.add(new Button("System Infos", "uri..", "NEWPAGE"));

			List<Element> bodyContent = new ArrayList<Element>();
			for (Device device : allDevices) {
				bodyContent.add(new Text(device.getName()));
				bodyContent.add(new Button("on", UriBuilder
						.fromResource(this.getClass())
						.path(this.getClass().getMethod("switchDevice",
								String.class, String.class))
						.build(device.getId(), "1").toASCIIString(), "ACTION"));
				bodyContent.add(new Button("off", "http://localhost:8080/"
						+ device.getId() + "/0/", "ACTION"));
			}

			List<Element> footerContent = new ArrayList<Element>();
			footerContent.add(new Text("Test TesT TEST"));
			footerContent.add(new Text("Test TesT TEST"));

			List<OverElement> overElement = new ArrayList<OverElement>();
			overElement.add(new NavBar(navbarButtons));
			overElement.add(new Body(bodyContent));
			overElement.add(new Footer(footerContent));

			DeviceOverview overview = new DeviceOverview(overElement);

			return overview;
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UriBuilderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			em.close();
		}
		return new DeviceOverview();
	}

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
	@Path("/{entityId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Device getDevice(@PathParam("entityId") String entityId) {
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("jRemotePi");
		EntityManager em = factory.createEntityManager();
		try {
			return em.find(Device.class, Long.parseLong(entityId));
		} finally {
			em.close();
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

	// @PUT
	// @Path("/{deviceName}/{systemId}/{deviceId}")
	// @Produces(MediaType.APPLICATION_JSON)
	// public Device putDevice(@PathParam("deviceName") String deviceName,
	// @PathParam("systemId") String systemId,
	// @PathParam("deviceId") String deviceId) {
	// EntityManagerFactory factory = Persistence
	// .createEntityManagerFactory("jRemotePi");
	// EntityManager em = factory.createEntityManager();
	// try {
	// em.getTransaction().begin();
	// Device newDevice = new Device();
	// newDevice.setName(deviceName);
	// newDevice.setSystemId(systemId);
	// newDevice.setDeviceId(deviceId);
	// Device mergedDevice = em.merge(newDevice);
	// em.getTransaction().commit();
	//
	// return em.find(Device.class, mergedDevice.getId());
	// } finally {
	// em.close();
	// }
	// }

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
