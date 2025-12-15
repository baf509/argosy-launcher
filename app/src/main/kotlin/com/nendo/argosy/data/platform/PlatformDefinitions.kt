package com.nendo.argosy.data.platform

import com.nendo.argosy.data.local.entity.PlatformEntity

data class PlatformDef(
    val id: String,
    val name: String,
    val shortName: String,
    val extensions: Set<String>,
    val sortOrder: Int
)

object PlatformDefinitions {

    private val platforms = listOf(
        // Nintendo consoles (1-9) - chronological
        PlatformDef("nes", "Nintendo Entertainment System", "NES", setOf("nes", "unf", "unif", "zip", "7z", "chd"), 1),
        PlatformDef("famicom", "Famicom", "Famicom", setOf("nes", "unf", "unif", "zip", "7z", "chd"), 1),
        PlatformDef("fds", "Famicom Disk System", "FDS", setOf("fds", "zip", "7z", "chd"), 2),
        PlatformDef("snes", "Super Nintendo", "SNES", setOf("sfc", "smc", "fig", "swc", "zip", "7z", "chd"), 3),
        PlatformDef("n64", "Nintendo 64", "N64", setOf("n64", "z64", "v64", "zip", "7z", "chd"), 4),
        PlatformDef("gc", "GameCube", "GameCube", setOf("iso", "gcm", "gcz", "rvz", "ciso", "zip", "7z", "chd"), 5),
        PlatformDef("ngc", "Nintendo GameCube", "GameCube", setOf("iso", "gcm", "gcz", "rvz", "ciso", "zip", "7z", "chd"), 5),
        PlatformDef("wii", "Nintendo Wii", "Wii", setOf("wbfs", "iso", "rvz", "gcz", "zip", "7z", "chd"), 6),
        PlatformDef("wiiu", "Nintendo Wii U", "Wii U", setOf("wud", "wux", "rpx", "wua", "zip", "7z", "chd"), 7),
        PlatformDef("switch", "Nintendo Switch", "Switch", setOf("nsp", "xci", "nsz", "xcz", "zip", "7z", "chd"), 8),

        // Nintendo handhelds (10-19) - chronological
        PlatformDef("gameandwatch", "Game & Watch", "G&W", emptySet(), 10),
        PlatformDef("gb", "Game Boy", "Game Boy", setOf("gb", "zip", "7z", "chd"), 11),
        PlatformDef("gbc", "Game Boy Color", "Game Boy Color", setOf("gbc", "zip", "7z", "chd"), 12),
        PlatformDef("virtualboy", "Virtual Boy", "VB", setOf("vb", "zip", "7z", "chd"), 13),
        PlatformDef("gba", "Game Boy Advance", "Game Boy Advance", setOf("gba", "zip", "7z", "chd"), 14),
        PlatformDef("nds", "Nintendo DS", "NDS", setOf("nds", "dsi", "zip", "7z", "chd"), 15),
        PlatformDef("dsi", "Nintendo DSi", "DSi", setOf("nds", "dsi", "zip", "7z", "chd"), 16),
        PlatformDef("3ds", "Nintendo 3DS", "3DS", setOf("3ds", "cia", "cxi", "app", "zip", "7z", "chd"), 17),
        PlatformDef("n3ds", "New Nintendo 3DS", "N3DS", setOf("3ds", "cia", "cxi", "app", "zip", "7z", "chd"), 18),

        // Sega consoles (20-29) - chronological
        PlatformDef("sg1000", "Sega SG-1000", "SG-1000", setOf("sg", "zip", "7z", "chd"), 20),
        PlatformDef("sms", "Sega Master System", "SMS", setOf("sms", "sg", "zip", "7z", "chd"), 21),
        PlatformDef("genesis", "Sega Genesis", "Genesis", setOf("md", "gen", "smd", "bin", "zip", "7z", "chd"), 22),
        PlatformDef("megadrive", "Sega Mega Drive", "Mega Drive", setOf("md", "gen", "smd", "bin", "zip", "7z", "chd"), 22),
        PlatformDef("scd", "Sega CD", "Sega CD", setOf("iso", "bin", "chd", "zip", "7z"), 23),
        PlatformDef("segacd", "Sega CD", "Sega CD", setOf("iso", "bin", "chd", "zip", "7z"), 23),
        PlatformDef("32x", "Sega 32X", "32X", setOf("32x", "zip", "7z", "chd"), 24),
        PlatformDef("sega32x", "Sega 32X", "32X", setOf("32x", "zip", "7z", "chd"), 24),
        PlatformDef("saturn", "Sega Saturn", "Saturn", setOf("iso", "bin", "cue", "chd", "zip", "7z"), 25),
        PlatformDef("dreamcast", "Sega Dreamcast", "Dreamcast", setOf("gdi", "cdi", "chd", "zip", "7z"), 26),
        PlatformDef("dc", "Sega Dreamcast", "Dreamcast", setOf("gdi", "cdi", "chd", "zip", "7z"), 26),
        PlatformDef("gg", "Sega Game Gear", "GG", setOf("gg", "zip", "7z", "chd"), 27),
        PlatformDef("gamegear", "Sega Game Gear", "GG", setOf("gg", "zip", "7z", "chd"), 27),
        PlatformDef("naomi", "Sega NAOMI", "NAOMI", setOf("zip", "7z", "chd"), 28),
        PlatformDef("atomiswave", "Sammy Atomiswave", "Atomiswave", setOf("zip", "7z", "chd"), 29),

        // Sony (30-39) - chronological
        PlatformDef("psx", "Sony PlayStation", "PS1", setOf("bin", "iso", "img", "chd", "pbp", "cue", "zip", "7z"), 30),
        PlatformDef("ps", "Sony PlayStation", "PS1", setOf("bin", "iso", "img", "chd", "pbp", "cue", "zip", "7z"), 30),
        PlatformDef("ps2", "Sony PlayStation 2", "PS2", setOf("iso", "bin", "chd", "gz", "cso", "zip", "7z"), 31),
        PlatformDef("psp", "Sony PlayStation Portable", "PSP", setOf("iso", "cso", "pbp", "zip", "7z", "chd"), 32),
        PlatformDef("ps3", "Sony PlayStation 3", "PS3", setOf("iso", "pkg", "zip", "7z"), 33),
        PlatformDef("vita", "Sony PlayStation Vita", "Vita", setOf("vpk", "mai", "zip", "7z", "chd"), 34),
        PlatformDef("psvita", "Sony PlayStation Vita", "Vita", setOf("vpk", "mai", "zip", "7z", "chd"), 34),
        PlatformDef("ps4", "Sony PlayStation 4", "PS4", setOf("pkg", "zip", "7z"), 35),
        PlatformDef("ps5", "Sony PlayStation 5", "PS5", emptySet(), 36),

        // Microsoft (40-49) - chronological
        PlatformDef("xbox", "Microsoft Xbox", "Xbox", setOf("iso", "xiso", "zip", "7z"), 40),
        PlatformDef("xbox360", "Microsoft Xbox 360", "X360", setOf("iso", "xex", "zip", "7z"), 41),
        PlatformDef("xboxone", "Microsoft Xbox One", "XB1", emptySet(), 42),

        // NEC (50-59)
        PlatformDef("tg16", "TurboGrafx-16", "TG16", setOf("pce", "zip", "7z", "chd"), 50),
        PlatformDef("pce", "PC Engine", "PCE", setOf("pce", "zip", "7z", "chd"), 50),
        PlatformDef("tgcd", "TurboGrafx-CD", "TG-CD", setOf("chd", "cue", "ccd", "zip", "7z"), 51),
        PlatformDef("pcfx", "PC-FX", "PC-FX", setOf("chd", "cue", "ccd", "zip", "7z"), 52),
        PlatformDef("supergrafx", "SuperGrafx", "SGX", setOf("pce", "sgx", "zip", "7z", "chd"), 53),

        // SNK (60-69)
        PlatformDef("neogeo", "Neo Geo", "Neo Geo", setOf("zip", "7z", "chd"), 60),
        PlatformDef("neogeomvs", "Neo Geo MVS", "MVS", setOf("zip", "7z", "chd"), 60),
        PlatformDef("neogeoaes", "Neo Geo AES", "AES", setOf("zip", "7z", "chd"), 61),
        PlatformDef("ngp", "Neo Geo Pocket", "NGP", setOf("ngp", "ngc", "zip", "7z", "chd"), 62),
        PlatformDef("ngpc", "Neo Geo Pocket Color", "NGPC", setOf("ngpc", "ngc", "zip", "7z", "chd"), 63),
        PlatformDef("neocd", "Neo Geo CD", "NGCD", setOf("chd", "cue", "iso", "zip", "7z"), 64),

        // Atari (70-79)
        PlatformDef("atari2600", "Atari 2600", "2600", setOf("a26", "bin", "zip", "7z", "chd"), 70),
        PlatformDef("atari5200", "Atari 5200", "5200", setOf("a52", "bin", "zip", "7z", "chd"), 71),
        PlatformDef("atari7800", "Atari 7800", "7800", setOf("a78", "bin", "zip", "7z", "chd"), 72),
        PlatformDef("atarist", "Atari ST", "ST", setOf("st", "stx", "zip", "7z"), 73),
        PlatformDef("lynx", "Atari Lynx", "Lynx", setOf("lnx", "zip", "7z", "chd"), 74),
        PlatformDef("jaguar", "Atari Jaguar", "Jaguar", setOf("j64", "jag", "zip", "7z", "chd"), 75),
        PlatformDef("jaguarcd", "Atari Jaguar CD", "Jag CD", setOf("chd", "cue", "zip", "7z"), 76),

        // Commodore (80-89)
        PlatformDef("c64", "Commodore 64", "C64", setOf("d64", "t64", "prg", "crt", "zip", "7z"), 80),
        PlatformDef("c128", "Commodore 128", "C128", setOf("d64", "d81", "prg", "zip", "7z"), 81),
        PlatformDef("amiga", "Commodore Amiga", "Amiga", setOf("adf", "ipf", "lha", "zip", "7z"), 82),
        PlatformDef("vic20", "Commodore VIC-20", "VIC-20", setOf("prg", "crt", "zip", "7z"), 83),

        // 3DO / Other 90s (90-99)
        PlatformDef("3do", "3DO", "3DO", setOf("iso", "chd", "cue", "zip", "7z"), 90),
        PlatformDef("cdi", "Philips CD-i", "CD-i", setOf("chd", "cue", "iso", "zip", "7z"), 91),

        // MSX (100-109)
        PlatformDef("msx", "MSX", "MSX", setOf("rom", "mx1", "mx2", "zip", "7z", "chd"), 100),
        PlatformDef("msx2", "MSX2", "MSX2", setOf("rom", "mx2", "zip", "7z", "chd"), 101),

        // Arcade (110-119)
        PlatformDef("arcade", "Arcade", "Arcade", setOf("zip", "7z", "chd"), 110),
        PlatformDef("mame", "MAME", "MAME", setOf("zip", "7z", "chd"), 110),
        PlatformDef("cps1", "Capcom CPS-1", "CPS1", setOf("zip", "7z"), 111),
        PlatformDef("cps2", "Capcom CPS-2", "CPS2", setOf("zip", "7z"), 112),
        PlatformDef("cps3", "Capcom CPS-3", "CPS3", setOf("zip", "7z"), 113),

        // PC (120-129)
        PlatformDef("dos", "DOS", "DOS", setOf("exe", "com", "bat", "zip", "7z", "chd"), 120),
        PlatformDef("scummvm", "ScummVM", "ScummVM", setOf("scummvm", "zip", "7z", "chd"), 121),
        PlatformDef("pc", "PC", "PC", emptySet(), 122),
        PlatformDef("windows", "Windows", "Windows", setOf("exe", "zip", "7z"), 123),

        // Bandai (130-139)
        PlatformDef("wonderswan", "WonderSwan", "WS", setOf("ws", "zip", "7z", "chd"), 130),
        PlatformDef("wonderswancolor", "WonderSwan Color", "WSC", setOf("wsc", "zip", "7z", "chd"), 131),

        // Other (140-149)
        PlatformDef("vectrex", "Vectrex", "Vectrex", setOf("vec", "zip", "7z", "chd"), 140),
        PlatformDef("coleco", "ColecoVision", "Coleco", setOf("col", "zip", "7z", "chd"), 141),
        PlatformDef("colecovision", "ColecoVision", "Coleco", setOf("col", "zip", "7z", "chd"), 141),
        PlatformDef("intellivision", "Intellivision", "Intv", setOf("int", "bin", "zip", "7z", "chd"), 142),
        PlatformDef("channelf", "Fairchild Channel F", "Channel F", setOf("bin", "chf", "zip", "7z"), 143),
        PlatformDef("odyssey2", "Magnavox Odyssey 2", "Odyssey 2", setOf("bin", "zip", "7z"), 144),

        // Streaming/Launcher (150+)
        PlatformDef("steam", "Steam", "Steam", emptySet(), 150),
    )

    private val platformMap = platforms.associateBy { it.id }
    private val extensionMap: Map<String, List<PlatformDef>>

    init {
        val extMap = mutableMapOf<String, MutableList<PlatformDef>>()
        platforms.forEach { platform ->
            platform.extensions.forEach { ext ->
                extMap.getOrPut(ext.lowercase()) { mutableListOf() }.add(platform)
            }
        }
        extensionMap = extMap
    }

    fun getAll(): List<PlatformDef> = platforms

    fun getById(id: String): PlatformDef? = platformMap[id]

    fun getPlatformsForExtension(extension: String): List<PlatformDef> =
        extensionMap[extension.lowercase()] ?: emptyList()

    fun toEntity(def: PlatformDef) = PlatformEntity(
        id = def.id,
        name = def.name,
        shortName = def.shortName,
        sortOrder = def.sortOrder,
        romExtensions = def.extensions.joinToString(","),
        isVisible = true
    )

    fun toEntities(): List<PlatformEntity> = platforms.map { toEntity(it) }
}
