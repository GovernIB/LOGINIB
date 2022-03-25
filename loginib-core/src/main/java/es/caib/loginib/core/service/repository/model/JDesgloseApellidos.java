package es.caib.loginib.core.service.repository.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.codec.binary.Base64;

import es.caib.loginib.core.api.model.login.DesgloseApellidos;

@Entity
@Table(name = "LIB_DESGLO", uniqueConstraints = @UniqueConstraint(columnNames = "LDS_NIF"))
public class JDesgloseApellidos implements IModelApi {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "LDS_NIF", unique = true, nullable = false)
	private String nif;

	@Column(name = "LDS_NOM", nullable = false)
	private String nombre;

	@Column(name = "LDS_APE")
	private String apellidoComp;

	@Column(name = "LDS_APE1", nullable = false)
	private String apellido1;

	@Column(name = "LDS_APE2", nullable = false)
	private String apellido2;

	@Column(name = "LDS_NOMCTO", nullable = false)
	private String nombreCompleto;

	@Column(name = "LDS_FCCREA", nullable = false)
	private Date fechaCreacion;

	@Column(name = "LDS_FCACT")
	private Date fechaActualizacion;

	public JDesgloseApellidos() {
		super();
	}

	/**
	 * @return the nif
	 */
	public String getNif() {
		return nif;
	}

	/**
	 * @param nif the nif to set
	 */
	public void setNif(String nif) {
		this.nif = nif;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the apellidoComp
	 */
	public String getApellidoComp() {
		return apellidoComp;
	}

	/**
	 * @param apellidoComp the apellidoComp to set
	 */
	public void setApellidoComp(String apellidoComp) {
		this.apellidoComp = apellidoComp;
	}

	/**
	 * @return the apellido1
	 */
	public String getApellido1() {
		return apellido1;
	}

	/**
	 * @param apellido1 the apellido1 to set
	 */
	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	/**
	 * @return the apellido2
	 */
	public String getApellido2() {
		return apellido2;
	}

	/**
	 * @param apellido2 the apellido2 to set
	 */
	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	/**
	 * @return the nombreCompleto
	 */
	public String getNombreCompleto() {
		return nombreCompleto;
	}

	/**
	 * @param nombreCompleto the nombreCompleto to set
	 */
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	/**
	 * @return the fechaCreacion
	 */
	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	/**
	 * @param fechaCreacion the fechaCreacion to set
	 */
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	/**
	 * @return the fechaActualizacion
	 */
	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	/**
	 * @param fechaActualizacion the fechaActualizacion to set
	 */
	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	public DesgloseApellidos toModel() {
		final DesgloseApellidos da = new DesgloseApellidos();
		da.setNif(this.nif);
		da.setApellido1(this.apellido1);
		da.setApellido2(this.apellido2);
		da.setApellidoComp(this.apellidoComp);
		da.setNombre(this.nombre);
		da.setNombreCompleto(this.nombreCompleto);
		da.setFechaActualizacion(this.fechaActualizacion);
		da.setFechaCreacion(this.fechaCreacion);
		return da;
	}

	public static JDesgloseApellidos fromModelStatic(final DesgloseApellidos da) {
		JDesgloseApellidos jda = null;
		if (da != null) {
			jda = new JDesgloseApellidos();
			jda.fromModel(da);
		}
		return jda;
	}

	public void fromModel(final DesgloseApellidos da) {
		if (da != null) {
			this.setNif(da.getNif());
			this.setApellido1(da.getApellido1());
			this.setApellido2(da.getApellido2());
			this.setApellidoComp(da.getApellidoComp());
			this.setNombre(da.getNombre());
			this.setNombreCompleto(da.getNombreCompleto());
			this.setFechaActualizacion(da.getFechaActualizacion());
			this.setFechaCreacion(da.getFechaCreacion());
		}
	}

	public static String encodeSql(final String sqlPlain) {
		return Base64.encodeBase64String(sqlPlain.getBytes());
	}

	public static String decodeSql(final String sqlEncoded) {
		if (sqlEncoded == null) {
			return null;
		} else {
			return new String(Base64.decodeBase64(sqlEncoded));
		}
	}

	/**
	 * Clona un dominio.
	 *
	 * @param dominio
	 * @param nuevoIdentificador
	 * @param jareas
	 * @param jfuenteDatos
	 * @param jentidad
	 * @return
	 */
	public static JDesgloseApellidos clonar(final JDesgloseApellidos jda1) {
		JDesgloseApellidos jda2 = null;
		if (jda1 != null) {
			jda1.setNif(jda2.getNif());
			jda1.setApellido1(jda2.getApellido1());
			jda1.setApellido2(jda2.getApellido2());
			jda1.setApellidoComp(jda2.getApellidoComp());
			jda1.setNombre(jda2.getNombre());
			jda1.setNombreCompleto(jda2.getNombreCompleto());
			jda1.setFechaActualizacion(jda2.getFechaActualizacion());
			jda1.setFechaCreacion(jda2.getFechaCreacion());
		}
		return jda2;
	}
}
