// ejemplos/08-tipos-utilitarios.ts

interface Usuario {
  id: number;
  nombre: string;
  email: string;
  activo: boolean;
}

type UsuarioParcial = Partial<Usuario>;
type UsuarioBasico = Pick<Usuario, "id" | "nombre">;
type UsuarioSinEmail = Omit<Usuario, "email">;
type MapaUsuarios = Record<string, Usuario>;

const uParcial: UsuarioParcial = { nombre: "Ana" };
const uBasico: UsuarioBasico = { id: 1, nombre: "Ana" };
const uSinEmail: UsuarioSinEmail = { id: 1, nombre: "Ana", activo: true };

const usuariosPorId: MapaUsuarios = {
  "1": { id: 1, nombre: "Ana", email: "ana@example.com", activo: true },
};

// --- Tipos condicionales ---
type EsString<T> = T extends string ? "es string" : "no es string";

type Resultado1 = EsString<string>;
type Resultado2 = EsString<number>;

type IdDe<T> = T extends { id: infer U } ? U : never;

type IdUsuario = IdDe<{ id: number; nombre: string }>;
