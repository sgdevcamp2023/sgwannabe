import { FastifyPluginAsync } from "fastify";

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

      const fileName = part?.filename;

      const body = new FormData();
      body.set("upload", blob, fileName);

      await fetch(fastify.config.STORAGE_UPLOAD_URL, {
        method: "POST",
        body,
      });
    } catch (err) {
      reply.code(400).send(err);
      return;
    }

    reply.status(200).send({
      message: "success",
    });
  });
};

export default upload;
