// ejemplos/suma.test.ts

import { sumar } from "./suma";

test("sumar 1 + 2 debe dar 3", () => {
  const resultado = sumar(1, 2);
  expect(resultado).toBe(3);
});
