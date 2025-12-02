// jest.config.cjs
/** @type {import('jest').Config} */
module.exports = {
  preset: "ts-jest",
  testEnvironment: "node",
  testMatch: ["**/?(*.)+(test).ts"],
  rootDir: ".",
};
