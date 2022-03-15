package me.altair.event;

import me.altair.Start;
import me.altair.commands.TestCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.requests.restaction.ChannelAction;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import net.dv8tion.jda.api.utils.TimeUtil;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ButtonListener extends ListenerAdapter {
    public static String ChannelId;
    public static String ChannelName;
    public void onButtonInteraction(@Nonnull ButtonInteractionEvent event) {

        event.deferEdit().queue();
        ChannelName = "ticket-" + event.getMember().getEffectiveName() +"-"+ event.getMember().getId();
        switch(event.getButton().getId()) {
            case "Ticket":
                if (event.getJDA().getTextChannels().stream().anyMatch(channel -> channel.getName().equalsIgnoreCase(ChannelName))) {
                    event.getMember().getGuild().getTextChannelById(ChannelId).sendMessage(event.getMember().getAsMention() + " twój ticket już istnieje!").queue();
                    return;
                } else
                    event.getMember().getGuild().getCategoryById("ID kategorii w której mają być tworzone tickety").createTextChannel(ChannelName)
                            .addPermissionOverride(event.getMember(), EnumSet.of(Permission.VIEW_CHANNEL), null)
                            .addPermissionOverride(event.getMember().getGuild().getPublicRole(), null, EnumSet.of(Permission.VIEW_CHANNEL))
                            .queue(textChannel -> {
                                ChannelId = textChannel.getId();
                                EmbedBuilder builder;
                                String[] args = event.getMessage().getContentRaw().split(" ");
                                builder = new EmbedBuilder();
                                builder.setTitle("Ticket " + event.getMember().getEffectiveName() + " ");
                                builder.setDescription("Nadmierne pingowanie administracji = warn/mute!");
                                builder.setColor(Color.CYAN.getRGB());
                                builder.setAuthor("Bot", event.getMessage().getAuthor().getAvatarUrl(), event.getMessage().getAuthor().getAvatarUrl());
                                event.getMember().getGuild().getTextChannelById(ChannelId).sendMessageEmbeds(builder.build(), new MessageEmbed[0]).setActionRows(ActionRow.of(sendButton())).queue();
                                event.getMember().getGuild().getTextChannelById(ChannelId).sendMessage(event.getMember().getAsMention()).queue();
                            });

                break;
            case "Close":
                try {
                    event.getMember().getGuild().getTextChannelById(ChannelId).sendMessage(event.getMember().getAsMention() + " Zamknąłeś ticketa!").queue();
                    TimeUnit.SECONDS.sleep(3);
                    event.getTextChannel().delete().queue();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
        }

     }


    private static List<Button> sendButton() {
        List<Button> buttons = new ArrayList<>();
        buttons.add(Button.danger("Close", "Zamknij ticketa!"));

        return buttons;
    }


}
