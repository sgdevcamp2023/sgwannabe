import fp from "fastify-plugin";
import fastifyEnv, { FastifyEnvOptions } from "@fastify/env";

export default fp<FastifyEnvOptions>(async (fastify) => {
  const schema = {
    type: "object",
    required: ["STORAGE_URL"],
    properties: {
      STORAGE_URL: {
        type: "string",
        default: "http://localhost:30000",
      },
    },
  };

  const options = {
    schema: schema,
  };

  fastify.register(fastifyEnv, options);
});

declare module "fastify" {
  export interface FastifyInstance {
    config: {
      STORAGE_URL: string;
    };
  }
}
