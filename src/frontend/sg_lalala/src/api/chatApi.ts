import apiHook from "./axios";

const chatApi = {
  getAllChatList: async () => {
    try {
      const response = await apiHook.chat.get(`v1/api/rooms/`);
      return response.data;
    } catch (error) {
      console.error("에러 발생:", error);
      throw error;
    }
  },
  getChatList: async ({ uid }: { uid: number }) => {
    try {
      const response = await apiHook.chat.get(
        `v1/api/rooms/unjoined?uid=${uid}`
      );
      return response.data;
    } catch (error) {
      console.error("에러 발생:", error);
      throw error;
    }
  },
  getJoinedChatList: async ({ uid }: { uid: number }) => {
    try {
      const response = await apiHook.chat.get(`v1/api/rooms/joined?uid=${uid}`);
      return response.data;
    } catch (error) {
      console.error("에러 발생:", error);
      throw error;
    }
  },
  getRejoin: async ({
    roomId,
    readMsgId,
  }: {
    roomId: string;
    readMsgId: string | undefined;
  }) => {
    try {
      const response = await apiHook.chat.get(
        `v1/api/chat/rooms/joined/${roomId}?readMsgId=${readMsgId}`
      );
      return response.data;
    } catch (error) {
      console.error("에러 발생:", error);
      throw error;
    }
  },
  postExitRoom: async ({ roomId, uid }: { roomId: string; uid: number }) => {
    try {
      const response = await apiHook.chat.post(
        `v1/api/rooms/exit/${roomId}?uid=${uid}`
      );
      return response.data;
    } catch (error) {
      console.error("에러 발생:", error);
      throw error;
    }
  },
  putLeaveRoom: async ({ roomId, uid }: { roomId: string; uid: number }) => {
    try {
      const response = await apiHook.chat.put(`v1/api/rooms/leave`, {
        roomId: roomId,
        uid: uid,
      });
      return response.data;
    } catch (error) {
      console.error("에러 발생:", error);
      throw error;
    }
  },
};

export default chatApi;
