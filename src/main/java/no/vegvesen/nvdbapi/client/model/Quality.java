/*
 * Copyright (c) 2015-2017, Statens vegvesen
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package no.vegvesen.nvdbapi.client.model;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Quality {
    private final MeasurementMethod measurementMethod;
    private final Integer accuracy;
    private final Visibility visibility;
    private final HeightMeasurementMethod heightMethod;
    private final Integer heightAccuracy;
    private final Integer tolerance;

    public Quality(MeasurementMethod measurementMethod,
                   Integer accuracy,
                   HeightMeasurementMethod heightMethod,
                   Integer heightAccuracy,
                   Integer tolerance,
                   Visibility visibility) {
        this.measurementMethod = measurementMethod;
        this.accuracy = accuracy;
        this.visibility = visibility;
        this.heightMethod = heightMethod;
        this.heightAccuracy = heightAccuracy;
        this.tolerance = tolerance;
    }

    public MeasurementMethod getMethod() {
        return measurementMethod;
    }

    public Integer getAccuracy() {
        return accuracy;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public HeightMeasurementMethod getHeightMethod() {
        return heightMethod;
    }

    public Integer getHeightAccuracy() {
        return heightAccuracy;
    }

    public Integer getTolerance() {
        return tolerance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quality quality = (Quality) o;
        return Objects.equals(measurementMethod, quality.measurementMethod) &&
            Objects.equals(accuracy, quality.accuracy) &&
            Objects.equals(visibility, quality.visibility) &&
            Objects.equals(heightMethod, quality.heightMethod) &&
            Objects.equals(heightAccuracy, quality.heightAccuracy) &&
            Objects.equals(tolerance, quality.tolerance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(measurementMethod, accuracy, visibility, heightMethod, heightAccuracy, tolerance);
    }

    @Override
    public String toString() {
        return "Quality{" +
            "method=" + measurementMethod +
            ", accuracy=" + accuracy +
            ", visibility=" + visibility +
            ", heightMethod=" + heightMethod +
            ", heightAccuracy=" + heightAccuracy +
            ", tolerance=" + tolerance +
            '}';
    }

    public enum MeasurementMethod {
        GNSSFASEMAALING_ANDRE_METODER("GNSS: Fasemåling, andre metoder", "Innmålt med satellittbaserte systemer for navigasjon og posisjonering med global dekning (f.eks GPS, GLONASS, GALILEO): Fasemåling andre metoder.", "GNSSFasemålingAndreMetoder", 94),
        GNSSFASEMAALING_FLOAT("GNSS: Fasemåling , float-løsning", "Innmålt med satellittbaserte systemer for navigasjon og posisjonering med global dekning (f.eks GPS, GLONASS, GALILEO). Fasemåling float-løsning", "GNSSFasemålingFloat-løsning", 97),
        GNSSFASEMAALING("GNSS: Fasemåling RTK", "Innmålt med satellittbaserte systemer for navigasjon og posisjonering med global dekning (f.eks GPS, GLONASS, GALILEO).: Fasemåling RTK (realtids kinematisk måling)", "GNSSFasemålingRTK", 96),
        GNSSFASEMAALING_STATISK_MAALING("GNSS: Fasemåling, statisk måling", "Innmålt med satellittbaserte systemer for navigasjon og posisjonering med global dekning (f.eks GPS, GLONASS, GALILEO): Fasemåling statisk måling.", "GNSSFasemålingStatiskMåling", 93),
        GNSSKODEMAALING_ENKLE_MAALINGER("GNSS: Kodemåling, enkle målinger", "Innmålt med satellittbaserte systemer for navigasjon og posisjonering med global dekning (f.eks GPS, GLONASS, GALILEO): Kodemåling, enkle målinger.", "GNSSKodemålingEnkleMålinger", 92),
        GNSSKODEMAALING_RELATIVE_MAALINGER("GNSS: Kodemåling, relative målinger", "Innmålt med satellittbaserte systemer for navigasjon og posisjonering med global dekning (f.eks GPS, GLONASS, GALILEO): Kodemåling, relative målinger.", "GNSSKodemålingRelativeMålinger", 91),
        AEROTRIANGULERT("Aerotriangulert", "Punkt beregnet ved aerotriangulering    -- Definition --   Point calculated by aerotriangulation", "aerotriangulert", 21),
        BEREGNET("Beregnet", "Beregnet, uspesifisert hvordan", "beregnet", 69),
        BILBAAREN_LASER("Bilbåren laser", "Målt med laserskanner plassert i kjøretøy", "bilbårenLaser", 37),
        DIGITALISERINGBORD_FLYBILDE_FILM("Digitaliseringbord: Flybilde, film", "Geometri overført fra flybilde ved hjelp av manuell registrering på et digitaliseringsbord. Bildemedium er film", "digitaliseringbordFlybildeFilm", 43),
        DIGITALISERINGBORD_FLYBILDE_FOTOKOPI("Digitaliseringbord: Flybilde, fotokopi", "Geometri overført fra flybilde ved hjelp av manuell registrering på et digitaliseringsbord. Bildemedium er fotokopi", "digitaliseringbordFlybildeFotokopi", 44),
        DIGITALISERINGBORD_ORTOFOTO_ELLER_FLYBILDE("Digitaliseringbord: Ortofoto eller flybilde", "Geometri overført fra ortofoto eller flybilde ved hjelp av manuell registrering på et digitaliseringsbord, uspesifisert bildemedium", "digitaliseringbordOrtofotoEllerFlybilde", 40),
        DIGITALISERINGBORD_ORTOFOTO_FILM("Digitaliseringbord: Ortofoto, film", "Geometri overført fra ortofoto ved hjelp av manuell registrering på et digitaliseringsbord. Bildemedium er film", "digitaliseringbordOrtofotoFilm", 41),
        DIGITALISERINGBORD_ORTOFOTO_FOTOKOPI("Digitaliseringbord: Ortofoto, fotokopi", "Geometri overført fra ortofoto ved hjelp av manuell registrering på et digitaliseringsbord. Bildemedium er fotokopi", "digitaliseringbordOrtofotoFotokopi", 42),
        DIGITALISERINGSBORD_KART("Digitaliseringsbord: Kart", "Geometri overført fra kart ved hjelp av manuell registrering på et digitaliseringsbord, medium uspesifisert", "digitaliseringsbordKart", 50),
        DIGITALISERINGSBORD_KART_BLYANTORIGINAL("Digitaliseringsbord: Kart, blyantoriginal", "Geometri overført fra kart ved hjelp av manuell registrering på et digitaliseringsbord. Kartmedium er blyantoriginal", "digitaliseringsbordKartBlyantoriginal", 51),
        DIGITALISERINGSBORD_KART_PAPIRKOPI("Digitaliseringsbord: Kart, papirkopi", "Geometri overført fra kart ved hjelp av manuell registrering på et digitaliseringsbord. Kartmedium er papirkopi", "digitaliseringsbordKartPapirkopi", 55),
        DIGITALISERINGSBORD_KART_RISSEFOILE("Digitaliseringsbord: Kart, rissefoile", "Geometri overført fra kart ved hjelp av manuell registrering på et digitaliseringsbord. Kartmedium er rissefolie", "digitaliseringsbordKartRissefoile", 52),
        DIGITALISERINGSBORD_KART_TRANSPARENT_FOILE_GOD_KVALITET("Digitaliseringsbord: Kart, transparent foile, god kvalitet", "Geometri overført fra kart ved hjelp av manuell registrering på et digitaliseringsbord. Kartmedium er transparent folie av god kvalitet, samkopi", "digitaliseringsbordKartTransparentFoileGodKvalitet", 53),
        DIGITALISERINGSBORD_KART_TRANSPARENT_FOILE_MINDRE_GOD_KVALITET("Digitaliseringsbord: Kart, transparent foile, mindre god kvalitet", "Geometri overført fra kart ved hjelp av manuell registrering på et digitaliseringsbord. Kartmedium er transparent folie av mindre god kvalitet, samkopi", "digitaliseringsbordKartTransparentFoileMindreGodKvalitet", 54),
        DIGITALISERT_PAA_SKJERM_FRA_ANDRE_DIGITALE_RASTERDATA("Digitalisert på skjerm fra andre digitale rasterdata", "", "digitalisertPåSkjermFraAndreDigitaleRasterdata", 47),
        DIGITALISERT_PAA_SKJERM_FRA_ORTOFOTO("Digitalisert på skjerm fra ortofoto", "Geometri overført fra ortofoto ved hjelp av manuell registrering på skjerm", "digitalisertPåSkjermFraOrtofoto", 45),
        DIGITALISERT_PAA_SKJERM_FRA_SATELLITTBILDE("Digitalisert på skjerm fra satellittbilde", "Geometri overført fra satellittbilde ved hjelp av manuell registrering på skjerm", "digitalisertPåSkjermFraSatellittbilde", 46),
        DIGITALISERT_PAA_SKJERM_FRA_SKANNET_KART("Digitalisert på skjerm fra skannet kart", "Geometri overført fra kart ved hjelp av manuell registrering på skjerm, medium skannet kart (raster), samkopi", "digitalisertPåSkjermFraSkannetKart", 56),
        DIGITALISERT_PAA_SKJERM_FRA_TOLKNING_AV_SEISMIKK("Digitalisert på skjerm fra tolkning av seismikk", "", "digitalisertPåSkjermFraTolkningAvSeismikk", 48),
        FASTSATT_PUNKT("Fastsatt punkt", "Punkt fastsatt ut fra et grunnlag (kart, bilde), f.eks ved partenes enighet ved en oppmålingsforretning", "fastsattPunkt", 77),
        FASTSATT_VED_DOM_ELLER_KONGELIG_RESOLUSJON("Fastsatt ved dom eller kongelig resolusjon", "Geometri fastsatt ved dom, lov, traktat eller kongelig resolusjon", "fastsattVedDomEllerKongeligResolusjon", 78),
        FLYBAAREN_LASERSCANNER("Flybåren laserscanner", "Målt med laserskanner fra fly", "flybårenLaserscanner", 36),
        FRIHAANDSTEGNING("Frihåndstegning", "Digitalisert ut fra frihåndstegning.  Frihåndstegning er basert på svært grovt grunnlag eller ikke noe grunnlag", "frihåndstegning", 80),
        FRIHAANDSTEGNING_PAA_KART("Frihåndstegning på kart", "Digitalisert fra krokering på kart, dvs grovt skissert på kart", "frihåndstegningPåKart", 81),
        FRIHAANDSTEGNING_PAA_SKJERM("Frihåndstegning på skjerm", "Digitalisert ut fra frihåndstegning (direkte på skjerm). Frihåndstegning er basert på svært grovt grunnlag eller ikke noe grunnlag", "frihåndstegningPåSkjerm", 82),
        GENERERTE_DATA_FRA_ANNEN_GEOMETRI("Genererte data: Fra annen geometri", "Genererte data: Sirkelgeometri, korridor eller annen geometri generert ut fra f.eks et punkt eller en linje (f.eks midtlinje veg)", "genererteDataFraAnnenGeometri", 63),
        GENERERTE_DATA_GENERALISERING("Genererte data: Generalisering", "Genererte data: Generalisering", "genererteDataGeneralisering", 64),
        GENERERTE_DATA_INTERPOLASJON("Genererte data (interpolasjon)", "Genererte data, interpolasjonsmetode. Ikke nærmere spesifisert", "genererteDataInterpolasjon", 60),
        GENERERTE_DATA_INTERPOLASJON_TERRENGMODELL("Genererte data (interpolasjon): Terrengmodell", "Genererte data, interpolasjonsmetode, fra terrengmodell", "genererteDataInterpolasjonTerrengmodell", 61),
        GENERERTE_DATA_INTERPOLASJON_VEKTET_MIDDEL("Genererte data (interpolasjon): Vektet middel", "Genererte data, interpolasjonsmetode, vektet middel", "genererteDataInterpolasjonVektetMiddel", 62),
        GENERERTE_DATA_SAMMENKNYTNINGSPUNKT_RANDPUNKT("Genererte data: Sammenknytningspunkt, randpunkt", "Genererte data: Sammenknytningspunkt (f.eks mellom ulike kartlegginger), randpunkt (f.eks mellom ulike kilder til kart)", "genererteDataSammenknytningspunktRandpunkt", 66),
        GENERERTE_DATA_SENTRALPUNKT("Genererte data: Sentralpunkt", "Genererte data: Sentralpunkt", "genererteDataSentralpunkt", 65),
        KOMBINASJON_AV_GNSSTREGHET("Kombinasjon av GNSS/Treghet", "Kombinasjon av GPS/Treghet", "kombinasjonAvGNSSTreghet", 95),
        KOORDINATER_HENTET_FRA_GAB("Koordinater hentet fra GAB", "Koordinater hentet fra GAB, forløperen til registerdelen av matrikkelen", "koordinaterHentetFraGAB", 67),
        KOORDINATER_HENTET_FRA_JREG("Koordinater hentet fra JREG", "Koordinater hentet fra JREG, jordregisteret", "koordinaterHentetFraJREG", 68),
        LINEAER_REFERANSE("Lineær referanse", "brukes for objekter som er stedfestet med lineær referanse, enten disse leveres med stedfesting kun som lineære referanser, eller med koordinatgeometri avledet fra lineære referanser", "lineærReferanse", 38),
        SCANNET_FRA_KART("Scannet fra kart", "Geometri overført fra kart maskinelt ved hjelp av skanner, uspesifisert kartmedium", "scannetFraKart", 30),
        SKANNET_FRA_KART_BLYANTORIGINAL("Skannet fra kart: Blyantoriginal", "Geometri overført fra kart maskinelt ved hjelp av skanner. Kartmedium er blyantoriginal", "skannetFraKartBlyantoriginal", 31),
        SKANNET_FRA_KART_PAPIRKOPI("Skannet fra kart: Papirkopi", "Geometri overført fra kart maskinelt ved hjelp av skanner. Kartmedium er papirkopi.", "skannetFraKartPapirkopi", 35),
        SKANNET_FRA_KART_RISSEFOLIE("Skannet fra kart: Rissefolie", "Geometri overført fra kart maskinelt ved hjelp av skanner. Kartmedium er rissefolie", "skannetFraKartRissefolie", 32),
        SKANNET_FRA_KART_TRANSPARENT_FOLIE_GOD_KVALITET("Skannet fra kart: Transparent folie, god kvalitet", "Geometri overført fra kart maskinelt ved hjelp av skanner. Kartmedium er transparent folie av  god kvalitet.", "skannetFraKartTransparentFolieGodKvalitet", 33),
        SKANNET_FRA_KART_TRANSPARENT_FOLIE_MINDRE_GOD_KVALITET("Skannet fra kart: Transparent folie, mindre god kvalitet", "Geometri overført fra kart maskinelt ved hjelp av skanner. Kartmedium er transparent folie av mindre god kvalitet", "skannetFraKartTransparentFolieMindreGodKvalitet", 34),
        SPESIELLE_METODER("Spesielle metoder", "Spesielle metoder, uspesifisert", "spesielleMetoder", 70),
        SPESIELLE_METODER_MAALT_MED_MAALEHJUL("Spesielle metoder: Målt med målehjul", "Spesielle metoder: Målt med målehjul", "spesielleMetoderMåltMedMålehjul", 73),
        SPESIELLE_METODER_MAALT_MED_STIGNINGSMAALER("Spesielle metoder: Målt med stigningsmåler", "Spesielle metoder: Målt med stigningsmåler", "spesielleMetoderMåltMedStigningsmåler", 74),
        SPESIELLE_METODER_MAALT_MED_STIKKSTANG("Spesielle metoder: Målt med stikkstang", "Spesielle metoder: Målt med stikkstang", "spesielleMetoderMåltMedStikkstang", 71),
        SPESIELLE_METODER_MAALT_MED_WATERSTANG("Spesielle metoder: Målt med waterstang", "Spesielle metoder: Målt med waterstang", "spesielleMetoderMåltMedWaterstang", 72),
        STEREOINSTRUMENT("Stereoinstrument", "Målt i stereoinstrument, uspesifisert instrument", "stereoinstrument", 20),
        STEREOINSTRUMENT_ANALYTISK_PLOTTER("Stereoinstrument: Analytisk plotter", "Målt i stereoinstrument, analytisk plotter", "stereoinstrumentAnalytiskPlotter", 22),
        STEREOINSTRUMENT_AUTOGRAF("Stereoinstrument: Autograf", "Målt i stereoinstrument, autograf, analogt instrument", "stereoinstrumentAutograf", 23),
        STEREOINSTRUMENT_DIGITALT("Stereoinstrument: Digitalt", "Målt i stereoinstrument, digitalt instrument", "stereoinstrumentDigitalt", 24),
        TATT_FRA_PLAN("Tatt fra plan", "Tatt fra plan eller godkjent tiltak", "tattFraPlan", 18),
        TERRENGMAALT_ORTOGONALMETODEN("Terrengmålt: Ortogonalmetoden", "Målt i terrenget, ortogonalmetoden", "terrengmåltOrtogonalmetoden", 14),
        TERRENGMAALT_TEODOLITT_OG_EL_AVSTANDSMAALER("Terrengmålt: Teodolitt og el avstandsmåler", "Målt i terrenget med teodolitt og elektronisk avstandsmåler", "terrengmåltTeodolittOgElAvstandsmåler", 12),
        TERRENGMAALT_TEODOLITT_OG_MAALEBAAND("Terrengmålt: Teodolitt og målebånd", "Målt i terrenget med teodolitt og målebånd", "terrengmåltTeodolittOgMålebånd", 13),
        TERRENGMAALT_TOTALSTASJON("Terrengmålt: Totalstasjon", "Målt i terrenget med totalstasjon", "terrengmåltTotalstasjon", 11),
        TERRENGMAALT_USPESIFISERT_MAALEINSTRUMENT("Terrengmålt: Uspesifisert måleinstrument", "Målt i terrenget , uspesifisert metode/måleinstrument", "terrengmåltUspesifisertMåleinstrument", 10),
        TREGHETSSTEDFESTING("Treghetsstedfesting", "Treghetsstedfesting", "treghetsstedfesting", 90),
        UKJENT_MAALEMETODE("Ukjent målemetode", "Målemetode er ukjent", "ukjentMålemetode", 99),
        UTMAAL("Utmål", "Punkt beregnet på bakgrunn av måling mot andre punkter, slik som to avstander eller avstand og retning    -- Definition --   Point calculated on the basis of other items, such as two distances or distance + direction.", "utmål", 15),
        VEKTORISERING_AV_LASERDATA("Vektorisering av laserdata", "Vektorisering fra laserdata, brukes også der vektoriseringen støttes av ortofoto", "vektoriseringAvLaserdata", 49),
        TERRESTRISK_LASERSKANNING("Terrestrisk laserskanning", "", "terrestriskLaserskanning", 39),
        BILDEMATCHING("Bildematching", "", "bildematching", 75),
        ANNET("Annet", "", "annet", 79),
        ANNET_19("Annet", "", "annet", 19),
        IKKE_REGISTRERT("Ikke registrert", "Ikke registrert", "ikkeRegistrert", 0);

        private final String presentationName;
        private final String description;
        private final String codeName;
        private final int code;
        private static final Map<Integer, MeasurementMethod> byId = Stream.of(MeasurementMethod.values()).collect(Collectors.toMap(m -> m.code, m -> m));

        MeasurementMethod(String presentationName, String description, String codeName, int code) {
            this.presentationName = presentationName;
            this.description = description;
            this.codeName = codeName;
            this.code = code;
        }

        public String getPresentationName() {
            return presentationName;
        }

        public String getDescription() {
            return description;
        }

        public String getCodeName() {
            return codeName;
        }

        public int getCode() {
            return code;
        }

        public static MeasurementMethod byId(int id){
            return byId.get(id);
        }
    }

    public enum HeightMeasurementMethod {
        GNSSFASEMAALING_ANDRE_METODER("GNSS: Fasemåling, andre metoder", "GNSSFasemålingAndreMetoder", 94),
        GNSSFASEMAALING_RTK("GNSS: Fasemåling RTK", "GNSSFasemålingRTK", 96),
        GNSSFASEMAALING_STATISK_MAALING("GNSS: Fasemåling, statisk måling", "GNSSFasemålingStatiskMåling", 93),
        GNSSKODEMAALING_ENKLE_MAALINGER("GNSS: Kodemåling, enkle målinger", "GNSSKodemålingEnkleMålinger", 92),
        GNSSKODEMAALING_RELATIVE_MAALINGER("GNSS: Kodemåling, relative målinger", "GNSSKodemålingRelativeMålinger", 91),
        AEROTRIANGULERT("Aerotriangulert", "aerotriangulert", 21),
        ANNET_SPESIFISERES_IFILHODE("Annet (spesifiseres i filhode)", "annetSpesifiseresIFilhode", 79),
        BEREGNET("Beregnet", "beregnet", 69),
        FASTSATT_VED_DOM_ELLER_KONGELIG_RESOLUSJON("Fastsatt ved dom eller kongelig resolusjon", "fastsattVedDomEllerKongeligResolusjon", 78),
        FLYBAAREN_LASERSCANNING("Flybåren laserscanning", "flybårenLaserscanning", 36),
        GENERERTE_DATA_FRA_ANNEN_GEOMETRI("Genererte data: Fra annen geometri", "genererteDataFraAnnenGeometri", 63),
        GENERERTE_DATA_GENERALISERING("Genererte data: Generalisering", "genererteDataGeneralisering", 64),
        GENERERTE_DATA_INTERPOLASJON("Genererte data (interpolasjon)", "genererteDataInterpolasjon", 60),
        GENERERTE_DATA_INTERPOLASJON_TERRENGMODELL("Genererte data (interpolasjon): Terrengmodell", "genererteDataInterpolasjonTerrengmodell", 61),
        GENERERTE_DATA_INTERPOLASJON_VEKTET_MIDDEL("Genererte data (interpolasjon): Vektet middel", "genererteDataInterpolasjonVektetMiddel", 62),
        GENERERTE_DATA_SAMMENKNYTNINGSPUNKT_RANDPUNKT("Genererte data: Sammenknytningspunkt, randpunkt", "genererteDataSammenknytningspunktRandpunkt", 66),
        KOMBINASJON_AV_GNSSTREGHET("Kombinasjon av GNSS/Treghet", "kombinasjonAvGNSSTreghet", 95),
        KOORDINATER_HENTET_FRA_GAB("Koordinater hentet fra GAB", "koordinaterHentetFraGAB", 67),
        KOORDINATER_HENTET_FRA_JREG("Koordinater hentet fra JREG", "koordinaterHentetFraJREG", 68),
        NIVELLEMENT("Nivellement", "nivellement", 15),
        SPESIELLE_METODER("Spesielle metoder", "spesielleMetoder", 70),
        SPESIELLE_METODER_MAALT_MED_STIGNINGSMAALER("Spesielle metoder: Målt med stigningsmåler", "spesielleMetoderMåltMedStigningsmåler", 74),
        STEREOINSTRUMENT("Stereoinstrument", "stereoinstrument", 20),
        STEREOINSTRUMENT_ANALYTISK_PLOTTER("Stereoinstrument: Analytisk plotter", "stereoinstrumentAnalytiskPlotter", 22),
        STEREOINSTRUMENT_AUTOGRAF("Stereoinstrument: Autograf", "stereoinstrumentAutograf", 23),
        STEREOINSTRUMENT_DIGITALT("Stereoinstrument: Digitalt", "stereoinstrumentDigitalt", 24),
        TATT_FRA_PLAN("Tatt fra plan", "tattFraPlan", 18),
        TERRENGMAALT_ORTOGONALMETODEN("Terrengmålt: Ortogonalmetoden", "terrengmåltOrtogonalmetoden", 14),
        TERRENGMAALT_TEODOLITT_OG_EL_AVSTANDSMAALER("Terrengmålt: Teodolitt og el avstandsmåler", "terrengmåltTeodolittOgElAvstandsmåler", 12),
        TERRENGMAALT_TEODOLITT_OG_MAALEBAAND("Terrengmålt: Teodolitt og målebånd", "terrengmåltTeodolittOgMålebånd", 13),
        TERRENGMAALT_TOTALSTASJON("Terrengmålt: Totalstasjon", "terrengmåltTotalstasjon", 11),
        TERRENGMAALT_USPESIFISERT_MAALEINSTRUMENT("Terrengmålt: Uspesifisert måleinstrument", "terrengmåltUspesifisertMåleinstrument", 10),
        TREGHETSSTEDFESTING("Treghetsstedfesting", "treghetsstedfesting", 90),
        BILBAAREN_LASERKANNING("Bilbåren laserskanning", "bilbårenLaserskanning", 37),
        VEKTORISERING_AV_LASERDATA("Vektorisering av laserdata", "vektoriseringAvLaserdata", 49),
        GPS_FASEMAALING_FLYT("GPS Fasemåling, flyt", "GNSSFasemålingFloat-løsning", 97),
        ANNET("Annet", "annet", 19),
        IKKE_REGISTRERT("Ikke registrert", "ikkeRegistrert", -1),
        UKJENT_MAALEMETODE("Ukjent målemetode", "ukjentMålemetode", 99);

        public final String presentationName;
        public final String codeName;
        public final int code;
        private static final Map<Integer, HeightMeasurementMethod> byId = Stream.of(HeightMeasurementMethod.values()).collect(Collectors.toMap(m -> m.code, m -> m));

        HeightMeasurementMethod(String presentationName, String codeName, int code) {
            this.presentationName = presentationName;
            this.codeName = codeName;
            this.code = code;
        }

        public String getCodeName() {
            return codeName;
        }

        public int getCode() {
            return code;
        }

        public String getPresentationName() {
            return presentationName;
        }

        public static HeightMeasurementMethod byId(Integer id){
            return byId.get(id);
        }
    }

    public enum Visibility {
        IKKE_REGISTRERT("ikkeRegistrert", "Ikke registrert", 99),
        FULLT_SYNLIG_OG_GJENFINNBAR_I_TERRENGET("fulltSynligOgGjenfinnbarITerrenget", "Fullt ut synlig/gjenfinnbar i terrenget Default", 0),
        DAARLIG_GJENFINNBAR_I_TERRENGET("dårligGjenfinnbarITerrenget", "Dårlig gjenfinnbar i terreng.", 1),
        MIDDELS_SYNLIG_I_FLYBILDE("middelsSynligIFlybilde", "Middels synlig i flybilde/modell", 2),
        DAARLIG_SYNLIG_I_FLYBILDE("dårligSynligIFlybilde", "Dårlig/ikke synlig i flybilde/modell", 3);

        public final String codeName;
        public final String description;
        public final int code;
        private static final Map<Integer, Visibility> byId = Stream.of(Visibility.values()).collect(Collectors.toMap(m -> m.code, m -> m));

        Visibility(String codeName, String description, int code) {
            this.codeName = codeName;
            this.description = description;
            this.code = code;
        }

        public String getCodeName() {
            return codeName;
        }

        public String getDescription() {
            return description;
        }

        public int getCode() {
            return code;
        }

        public static Visibility byId(int id){
            return byId.get(id);
        }
    }
}
