package me.altair.commands;

import me.altair.Start;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.IPermissionHolder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.awt.*;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class TicketsCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        EmbedBuilder builder;
        String[] args = event.getMessage().getContentRaw().split(" ");

            if (event.getMessage().getAuthor().getId().equals("Id twojego konta")) {
                if (args[0].equalsIgnoreCase("$$Ticket")) {
                    builder = new EmbedBuilder();
                    builder.setTitle("Tickety!");
                    builder.setDescription("Utwórz ticket jeżeli masz jakąś sprawe do administracji!\n" +
                            "\n" +
                            "Kliknij emotke niżej aby stworzyć ticketa!");
                    builder.setColor(Color.CYAN.getRGB());
                    builder.setAuthor("Bot", event.getMessage().getAuthor().getAvatarUrl(), event.getMessage().getAuthor().getAvatarUrl());
                    event.getTextChannel().sendMessageEmbeds(builder.build(), new MessageEmbed[0]).setActionRows(ActionRow.of(sendButtons())).queue();

                }

        }
    }

    private static List<Button> sendButtons() {
        List<Button> buttons = new ArrayList<>();
        buttons.add(Button.primary("Ticket", "Otwórz ticket").withEmoji(Emoji.fromMarkdown("\uD83D\uDCE9")));
        return buttons;
    }

}

