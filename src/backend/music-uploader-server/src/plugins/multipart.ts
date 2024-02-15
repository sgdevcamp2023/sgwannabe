import fp from "fastify-plugin";
import fastifyMultipart, { FastifyMultipartOptions } from "@fastify/multipart";

/**
 * This plugins adds some utilities to handle http errors
 *
 * @see https://github.com/fastify/fastify-sensible
 */
export default fp<FastifyMultipartOptions>(async (fastify) => {
  const MB_UNIT = 1024 * 1024;
  fastify.register(fastifyMultipart, {
    limits: {
      fileSize: 30 * MB_UNIT, // For multipart forms, the max file size in bytes
      files: 1, // Max number of file fields
    },
  });
});
