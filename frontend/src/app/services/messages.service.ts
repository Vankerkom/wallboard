import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {EMPTY, filter, Observable} from "rxjs";
import {Message} from "../models";
import {webSocket} from "rxjs/webSocket";

@Injectable({
  providedIn: 'root',
})
export class MessagesService {

  readonly messages$: Observable<Message> = webSocket<Message>('ws://127.0.0.1:8080/messages-stream').asObservable().pipe(
    filter(payload => 'message' in payload),
  );

  constructor(
    private http: HttpClient,
  ) {
  }

  public getMessages(): Observable<Message[]> {
    return this.http.get<Message[]>('/api/messages');
  }

}
