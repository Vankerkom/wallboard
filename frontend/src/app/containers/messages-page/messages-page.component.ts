import {ChangeDetectionStrategy, Component, computed, DestroyRef, signal} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterOutlet} from "@angular/router";
import {MessagesService} from "../../services/messages.service";
import {Message} from "../../models";
import {takeUntilDestroyed} from "@angular/core/rxjs-interop";

@Component({
  selector: 'app-messages-page',
  standalone: true,
  imports: [CommonModule, RouterOutlet],
  templateUrl: './messages-page.component.html',
  styleUrls: ['./messages-page.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class MessagesPageComponent {

  loading = signal(true);
  messages = signal<Message[]>([]);
  totalMessages = computed(() => this.messages().length);

  constructor(
    private readonly messagesService: MessagesService,
    private readonly destroyRef: DestroyRef,
  ) {
    this.messagesService.getMessages().subscribe(messages => {
      this.messages.set([...this.messages(), ...messages]);
      this.loading.set(false);
    });

    this.messagesService.messages$.pipe(
      takeUntilDestroyed(this.destroyRef)
    ).subscribe(message => {
      this.messages.set([...this.messages(), message]);
    });
  }

  protected trackByMessageId(_: number, message: Message): string {
    return message.id;
  }

}
