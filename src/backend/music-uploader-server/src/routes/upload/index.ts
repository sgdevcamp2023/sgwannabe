import { FastifyPluginAsync } from "fastify";
import crypto from "crypto";

const upload: FastifyPluginAsync = async (fastify, opts): Promise<void> => {
  fastify.post("/", async function (request, reply) {
    if (!request.isMultipart()) {
      reply.code(400).send(new Error("Request is not multipart"));
      return;
    }

    try {
      const part = await request.file();
      const buffer = await part!.toBuffer();
      const blob = new Blob([buffer!]);

      const encryptedFileName = `${crypto.randomUUID()}.${part?.filename
        .split(".")
        .pop()}`;
      const body = new FormData();
      body.set("upload", blob, encryptedFileName);

      await fetch(`${fastify.config.STORAGE_URL}/upload`, {
        method: "POST",
        body,
      });

      reply.code(200).send({
        url: `${fastify.config.STORAGE_URL}/${encryptedFileName}`,
      });
    } catch (err) {
      reply.code(400).send(err);
      return;
    }
  });
};

export default upload;
