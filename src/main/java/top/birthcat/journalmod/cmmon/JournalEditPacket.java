package top.birthcat.journalmod.cmmon;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;
import top.birthcat.journalmod.JournalMod;

import java.util.List;

import static top.birthcat.journalmod.cmmon.AttachmentTypes.ATT_PAGES;

/**
 * base on {@link net.minecraft.network.protocol.game.ServerboundEditBookPacket}
 */
public record JournalEditPacket(List<String> pages) implements CustomPacketPayload {

    public static final int MAX_LEN_PER_PAGE = 1024;
    public static final int MAX_PAGES = 100;

    public static final CustomPacketPayload.Type<JournalEditPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(JournalMod.MODID, "journal_edit_packet"));

    public static final StreamCodec<ByteBuf, JournalEditPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.stringUtf8(MAX_LEN_PER_PAGE).apply(ByteBufCodecs.list(MAX_PAGES)),
            JournalEditPacket::pages,
            JournalEditPacket::new
    );

    @Override
    @NotNull
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }


    public void handleOnServer(IPayloadContext ctx) {
        ctx.player().setData(ATT_PAGES, this.pages);
    }
}
