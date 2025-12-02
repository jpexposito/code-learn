// ejemplos/10-integracion-react-angular.ts

// --- React (ejemplo conceptual) ---
type SaludoProps = {
  nombre: string;
};

function Saludo({ nombre }: SaludoProps) {
  // Este JSX requiere configuración de React para compilar
  return <h1>Hola, {nombre}</h1>;
}

// --- Angular (ejemplo conceptual) ---
class UsuarioService {
  obtenerUsuario(id: number): Promise<{ id: number; nombre: string }> {
    return Promise.resolve({ id, nombre: "Usuario de ejemplo" });
  }
}

// --- Buenas prácticas ---
interface Usuario {
  id: number;
  nombre: string;
}

function crearUsuario(id: number, nombre: string): Usuario {
  return { id, nombre };
}

const nuevoUsuario = crearUsuario(1, "Ana");
console.log(nuevoUsuario);
