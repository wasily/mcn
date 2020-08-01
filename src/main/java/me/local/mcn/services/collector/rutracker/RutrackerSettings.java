package me.local.mcn.services.collector.rutracker;

import lombok.Getter;

import java.util.List;

@Getter
public class RutrackerSettings {
    private final List<String> moviesSubforums = List.of(
            "187", //Классика мирового кинематографа
            "1950", //Фильмы 2020
            "2090", //Фильмы до 1990 года
            "2091", //Фильмы 2001-2005
            "2092", //Фильмы 2006-2010
            "2093", //Фильмы 2011-2015
            "2200", //Фильмы 2016-2019
            "2221", //Фильмы 1991-2000
            "313", //Зарубежное кино (HD Video)
            "1457", //UHD Video
            "2199" //Классика мирового кинематографа (HD Video)
    );
    private final List<String> seriesForums = List.of(
            "189" //Зарубежные сериалы
    );
    private final String treeUrl = "http://api.rutracker.org/v1/static/cat_forum_tree";
    private final String subforumsUrl = "http://api.rutracker.org/v1/static/pvc/f/";
    private final String releasesUrl = "http://api.rutracker.org/v1/get_tor_topic_data?by=topic_id&val=";
    private final int requestLimit = 100;
}
