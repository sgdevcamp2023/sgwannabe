import { Client } from "@stomp/stompjs";

export interface StompClientType {
  chatSocket: Client;
  streamSocket: Client;
}
