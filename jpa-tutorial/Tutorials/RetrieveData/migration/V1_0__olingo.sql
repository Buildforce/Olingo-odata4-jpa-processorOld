SET schema "OLINGO";
-- create schema "OLINGO";

--------BUSINESS PARTNER---------------------------------------------------------------------------------------------------------
CREATE TABLE "OLINGO"."BusinessPartner"(
                                           "ID"                          VARCHAR(32) CONSTRAINT BusinessPartner_pkey PRIMARY KEY,
                                           "ETag"                        BIGINT,
                                           "Type"                        VARCHAR(2),
                                           "CustomString1"               VARCHAR(250),
                                           "CustomString2"               VARCHAR(250),
                                           "CustomNum1"                  DECIMAL(16, 5),
                                           "CustomNum2"                  DECIMAL(31, 0),
                                           "NameLine1"                   VARCHAR(250),
                                           "NameLine2"                   VARCHAR(250),
                                           "BirthDay"                    DATE,
                                           "Address.StreetName"          VARCHAR(200),
                                           "Address.StreetNumber"        VARCHAR(60),
                                           "Address.PostOfficeBox"       VARCHAR(60),
                                           "Address.City"                VARCHAR(100),
                                           "Address.PostalCode"          VARCHAR(60),
                                           "ADDRESS_REGIONCODEPUBLISHER" VARCHAR(10) NOT NULL,
                                           "ADDRESS_REGIONCODEID"        VARCHAR(10) NOT NULL,
                                           "ADDRESS_REGION"              VARCHAR(100),
                                           "Address.Country"             VARCHAR(100),
                                           "Telecom.Phone"               VARCHAR(100),
                                           "Telecom.Mobile"              VARCHAR(100),
                                           "Telecom.Fax"                 VARCHAR(100),
                                           "Telecom.Email"               VARCHAR(100),
                                           "CreatedBy"                   VARCHAR(32) NOT NULL,
                                           "CreatedAt"                   TIMESTAMP,
                                           "UpdatedBy"                   VARCHAR(32) NOT NULL,
                                           "UpdatedAt"                   TIMESTAMP,
                                           "Country"                     VARCHAR(4),
                                           "AbcClass"                    VARCHAR(1),
                                           "AccessRights"                INTEGER);

INSERT INTO "OLINGO"."BusinessPartner" VALUES ( '1', 0, '2', '', '', 6000.5, null, 'First Org.', '',    null, 'Test Road', '23',   '', 'Test City', '94321', 'ISO', '3166-2', 'US-CA', 'USA', '', '', '', '', '99', '2016-01-20 09:21:23', '', null, 'USA', 'A', null);
INSERT INTO "OLINGO"."BusinessPartner" VALUES ('10', 0, '2', '', '',   null, null, 'Tenth Org.', '',    null, 'Test Road', '12',   '', 'Test City', '03921', 'ISO', '3166-2', 'US-ME', 'USA', '', '', '', '', '99', '2016-01-20 09:21:23', '', null, 'DEU', null, null);
INSERT INTO "OLINGO"."BusinessPartner" VALUES ( '2', 0, '2', '', '',   null, null, 'Second Org.', '',   null, 'Test Road', '45',   '', 'Test City', '76321', 'ISO', '3166-2', 'US-TX', 'USA', '', '', '', '', '97', '2016-01-20 09:21:23', '', null, 'USA', 'B', null);
INSERT INTO "OLINGO"."BusinessPartner" VALUES ( '3', 0, '2', '', '',   null, null, 'Third Org.', '',    null, 'Test Road', '223',  '', 'Test City', '94322', 'ISO', '3166-2', 'US-CA', 'USA', '', '', '', '', '99', '2016-01-20 09:21:23', '', null, 'USA', 'C', null);
INSERT INTO "OLINGO"."BusinessPartner" VALUES ( '4', 0, '2', '', '',   null, null, 'Fourth Org.', '',   null, 'Test Road', '56',   '', 'Test City', '84321', 'ISO', '3166-2', 'US-UT', 'USA', '', '', '', '', '98', '2016-01-20 09:21:23', '', null, 'USA', 'C', null);
INSERT INTO "OLINGO"."BusinessPartner" VALUES ( '5', 0, '2', '', '',   null, null, 'Fifth Org.', '',    null, 'Test Road', '35',   '', 'Test City', '59321', 'ISO', '3166-2', 'US-MT', 'USA', '', '', '', '', '99', '2016-01-20 09:21:23', '', null, 'USA', null, null);
INSERT INTO "OLINGO"."BusinessPartner" VALUES ( '6', 0, '2', '', '',   null, null, 'Sixth Org.', '',    null, 'Test Road', '7856', '', 'Test City', '94324', 'ISO', '3166-2', 'US-CA', 'USA', '', '', '', '', '99', '2016-01-20 09:21:23', '', null, 'USA', null, null);
INSERT INTO "OLINGO"."BusinessPartner" VALUES ( '7', 0, '2', '', '',   null, null, 'Seventh Org.', '',  null, 'Test Road', '4',    '', 'Test City', '29321', 'ISO', '3166-2', 'US-SC', 'USA', '', '', '', '', '99', '2016-01-20 09:21:23', '', null, 'USA', null, null);
INSERT INTO "OLINGO"."BusinessPartner" VALUES ( '8', 0, '2', '', '',   null, null, 'Eighth Org.', '',   null, 'Test Road', '453',  '', 'Test City', '29221', 'ISO', '3166-2', 'US-SC', 'USA', '', '', '', '', '99', '2016-01-20 09:21:23', '', null, 'USA', null, null);
INSERT INTO "OLINGO"."BusinessPartner" VALUES ( '9', 0, '2', '', '',   null, null, 'Ninth Org.', '',    null, 'Test Road', '93',   '', 'Test City', '55021', 'ISO', '3166-2', 'US-MN', 'USA', '', '', '', '', '99', '2016-01-20 09:21:23', '', null, 'USA', null, null);
INSERT INTO "OLINGO"."BusinessPartner" VALUES ('97', 0, '1', '', '',   null, null, 'Urs', 'Müller',     null, 'Test Straße', '23', '', 'Test Dorf',  '4123', 'ISO', '3166-2', 'CH-BL', 'CHE', '', '', '', '', '99', '2016-07-20 09:21:23', '', null, 'CHE', null, 9);
INSERT INTO "OLINGO"."BusinessPartner" VALUES ('98', 0, '1', '', '',   null, null, 'John', 'Doe',       null, 'Test Road', '55',   '', 'Test City', '76321', 'ISO', '3166-2', 'US-TX', 'USA', '', '', '', '', '99', '2016-01-20 09:21:23', '', null, 'DEU', null, 2);
INSERT INTO "OLINGO"."BusinessPartner" VALUES ('99', 0, '1', '', '',   null, null, 'Max', 'Mustermann', null, 'Test Straße', '12', '', 'Teststadt', '10115', 'ISO', '3166-2', 'DE-BE', 'DEU', '', '', '', '', '99', '2016-01-20 09:21:23', '', null, 'DEU', null, 1);

--------BUSINESS PARTNER ROLE----------------------------------------------------------------------------------------------------
CREATE TABLE "OLINGO"."BusinessPartnerRole"
(
    "BusinessPartnerID" VARCHAR(32) NOT NULL
        CONSTRAINT fk_bpr_bpa REFERENCES "OLINGO"."BusinessPartner",
    "BusinessPartnerRole" VARCHAR(10) NOT NULL,
    PRIMARY KEY ("BusinessPartnerID", "BusinessPartnerRole")
);

INSERT INTO "OLINGO"."BusinessPartnerRole" VALUES ('1', 'A');
INSERT INTO "OLINGO"."BusinessPartnerRole" VALUES ('2', 'A');
INSERT INTO "OLINGO"."BusinessPartnerRole" VALUES ('2', 'C');
INSERT INTO "OLINGO"."BusinessPartnerRole" VALUES ('3', 'A');
INSERT INTO "OLINGO"."BusinessPartnerRole" VALUES ('3', 'B');
INSERT INTO "OLINGO"."BusinessPartnerRole" VALUES ('3', 'C');
INSERT INTO "OLINGO"."BusinessPartnerRole" VALUES ('7', 'C');

--------ADMINISTRATIVE DIVISION DESCRIPTION--------------------------------------------------------------------------------------
CREATE TABLE "OLINGO"."AdministrativeDivisionDescription"
(
    "CodePublisher" VARCHAR(10)  NOT NULL,
    "CodeID"        VARCHAR(10)  NOT NULL,
    "DivisionCode"  VARCHAR(10)  NOT NULL,
    "LanguageISO"   VARCHAR(4)   NOT NULL,
    "Description"   VARCHAR(100) NOT NULL,
    PRIMARY KEY ("CodePublisher", "CodeID", "DivisionCode", "LanguageISO")
);

INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS1', 'BE1', 'de', 'Region Brüssel-Hauptstadt');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS1', 'BE1', 'en', 'Brussels-Capital Region');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS1', 'BE2', 'de', 'Flämische Region');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS1', 'BE2', 'en', 'Flemish Region');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS1', 'BE3', 'de', 'Wallonische Region');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS1', 'BE3', 'en', 'Walloon Region');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS1', 'CH0', 'de', 'Schweiz');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS2', 'BE10', 'de', 'Region Brüssel-Hauptstadt');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS2', 'BE10', 'en', 'Brussels-Capital Region');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS2', 'BE21', 'de', 'Provinz Antwerpen');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS2', 'BE21', 'en', 'Antwerp');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS2', 'BE22', 'de', 'Provinz Limburg');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS2', 'BE22', 'en', 'Limburg');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS2', 'BE23', 'de', 'Provinz Ostflandern');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS2', 'BE23', 'en', 'East Flanders');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS2', 'BE24', 'de', 'Provinz Flämisch-Brabant');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS2', 'BE24', 'en', 'Flemish Brabant');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS2', 'BE25', 'de', 'Provinz Westflandern');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS2', 'BE25', 'en', 'West Flanders');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS2', 'BE31', 'de', 'Provinz Wallonisch-Brabant');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS2', 'BE31', 'en', 'Walloon Brabant');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS2', 'BE32', 'de', 'Provinz Hennegau');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS2', 'BE32', 'en', 'Hainaut');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS2', 'BE33', 'de', 'Provinz Lüttich');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS2', 'BE33', 'en', 'Liège');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS2', 'BE34', 'de', 'Provinz Luxemburg');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS2', 'BE34', 'en', 'Luxembourg (Belgium)');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS2', 'BE35', 'de', 'Provinz Namur');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS2', 'BE35', 'en', 'Namur');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE100', 'de', 'Bezirk Brüssel-Hauptstadt');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE100', 'en', 'Arrondissement of Brussels-Capital');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE211', 'de', 'Bezirk Antwerpen');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE211', 'en', 'Arrondissement of Antwerp');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE212', 'de', 'Bezirk Mechelen');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE212', 'en', 'Arrondissement of Mechelen');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE213', 'de', 'Bezirk Turnhout');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE213', 'en', 'Arrondissement of Turnhout');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE221', 'de', 'Bezirk Hasselt');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE221', 'en', 'Arrondissement of Hasselt');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE222', 'de', 'Bezirk Maaseik');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE222', 'en', 'Arrondissement of Maaseik');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE223', 'de', 'Bezirk Tongeren');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE223', 'en', 'Arrondissement of Tongeren');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE231', 'de', 'Bezirk Aalst');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE231', 'en', 'Arrondissement of Aalst');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE232', 'de', 'Bezirk Dendermonde');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE232', 'en', 'Arrondissement of Dendermonde');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE233', 'de', 'Bezirk Eeklo');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE233', 'en', 'Arrondissement of Eeklo');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE234', 'de', 'Bezirk Gent');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE234', 'en', 'Arrondissement of Ghent');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE235', 'de', 'Bezirk Oudenaarde');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE235', 'en', 'Arrondissement of Oudenaarde');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE236', 'de', 'Bezirk Sint-Niklaas');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE236', 'en', 'Arrondissement of Sint-Niklaas');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE241', 'de', 'Bezirk Halle-Vilvoorde');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE241', 'en', 'Arrondissement of Halle-Vilvoorde');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE242', 'de', 'Bezirk Löwen');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE242', 'en', 'Arrondissement of Leuven');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE251', 'de', 'Bezirk Brügge');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE251', 'en', 'Arrondissement of Bruges');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE252', 'de', 'Bezirk Diksmuide');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE252', 'en', 'Arrondissement of Diksmuide');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE253', 'de', 'Bezirk Ypern');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE253', 'en', 'Arrondissement of Ypres');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE254', 'de', 'Bezirk Kortrijk');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE254', 'en', 'Arrondissement of Kortrijk');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE255', 'de', 'Bezirk Ostende');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE255', 'en', 'Arrondissement of Ostend');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE256', 'de', 'Bezirk Roeselare');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE256', 'en', 'Arrondissement of Roeselare');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE257', 'de', 'Bezirk Tielt');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE257', 'en', 'Arrondissement of Tielt');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE258', 'de', 'Bezirk Veurne');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE258', 'en', 'Arrondissement of Veurne');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE310', 'de', 'Bezirk Nivelles');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE310', 'en', 'Arrondissement of Nivelles');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE321', 'de', 'Bezirk Ath');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE321', 'en', 'Arrondissement of Ath');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE322', 'de', 'Bezirk Charleroi');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE322', 'en', 'Arrondissement of Charleroi');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE323', 'de', 'Bezirk Mons');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE323', 'en', 'Arrondissement of Mons');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE324', 'de', 'Bezirk Mouscron');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE324', 'en', 'Arrondissement of Mouscron');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE325', 'de', 'Bezirk Soignies');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE325', 'en', 'Arrondissement of Soignies');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE326', 'de', 'Bezirk Thuin');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE326', 'en', 'Arrondissement of Thuin');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE327', 'de', 'Bezirk Tournai');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE327', 'en', 'Arrondissement of Tournai');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE331', 'de', 'Bezirk Huy');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE331', 'en', 'Arrondissement of Huy');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE332', 'de', 'Bezirk Lüttich');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE332', 'en', 'Arrondissement of Liège');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE334', 'de', 'Bezirk Waremme');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE334', 'en', 'Arrondissement of Waremme');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE335', 'de', 'Bezirk Verviers – frz. Sprachgebiet');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE335', 'en', 'Arrondissement of Verviers, municipalities of the French Community');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE336', 'de', 'Bezirk Verviers – deu. Sprachgebiet');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE336', 'en', 'Arrondissement of Verviers,municipalities of the German Community');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE341', 'de', 'Bezirk Arlon');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE341', 'en', 'Arrondissement of Arlon');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE342', 'de', 'Bezirk Bastogne');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE342', 'en', 'Arrondissement of Bastogne');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE343', 'de', 'Bezirk Marche-en-Famenne');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE343', 'en', 'Arrondissement of Marche-en-Famenne');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE344', 'de', 'Bezirk Neufchâteau');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE344', 'en', 'Arrondissement of Neufchâteau');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE345', 'de', 'Bezirk Virton');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE345', 'en', 'Arrondissement of Virton');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE351', 'de', 'Bezirk Dinant');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE351', 'en', 'Arrondissement of Dinant');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE352', 'de', 'Bezirk Namur');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE352', 'en', 'Arrondissement of Namur');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE353', 'de', 'Bezirk Philippeville');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('Eurostat', 'NUTS3', 'BE353', 'en', 'Arrondissement of Philippeville');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-1', 'BEL', 'de', 'Belgien');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-1', 'BEL', 'en', 'Belgium');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-1', 'CHE', 'de', 'Schweiz');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-1', 'CHE', 'en', 'Switzerland');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-1', 'DEU', 'de', 'Deutschland');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-1', 'DEU', 'en', 'Germany');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-1', 'USA', 'de', 'Vereinigte Staaten von Amerika');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-1', 'USA', 'en', 'United States of America');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'CH-AG', 'de', 'Aargau');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'CH-AI', 'de', 'Appenzell Innerrhoden');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'CH-AR', 'de', 'Appenzell Ausserrhoden');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'CH-BE', 'de', 'Bern');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'CH-BL', 'de', 'Basel-Landschaft');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'CH-BS', 'de', 'Basel-Stadt');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'CH-FR', 'de', 'Freiburg');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'CH-GE', 'de', 'Genf');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'CH-GL', 'de', 'Glarus');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'CH-GR', 'de', 'Graubünden');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'CH-JU', 'de', 'Jura');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'CH-LU', 'de', 'Luzern');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'CH-NE', 'de', 'Neuenburg');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'CH-NW', 'de', 'Nidwalden');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'CH-OW', 'de', 'Obwalden');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'CH-SG', 'de', 'St. Gallen');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'CH-SH', 'de', 'Schaffhausen');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'CH-SO', 'de', 'Solothurn');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'CH-SZ', 'de', 'Schwyz');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'CH-TG', 'de', 'Thurgau');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'CH-TI', 'de', 'Tessin');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'CH-UR', 'de', 'Uri');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'CH-VD', 'de', 'Waadt');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'CH-VS', 'de', 'Wallis');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'CH-ZG', 'de', 'Zug');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'CH-ZH', 'de', 'Zürich');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'DE-BB', 'de', 'Brandenburg');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'DE-BB', 'en', 'Brandenburg');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'DE-BE', 'de', 'Berlin');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'DE-BE', 'en', 'Berlin');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'DE-BW', 'de', 'Baden-Württemberg');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'DE-BW', 'en', 'Baden-Württemberg');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'DE-BY', 'de', 'Bayern Bayern');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'DE-BY', 'en', 'Bavaria');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'DE-HB', 'de', 'Bremen');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'DE-HB', 'en', 'Bremen');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'DE-HE', 'de', 'Hessen');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'DE-HE', 'en', 'Hesse');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'DE-HH', 'de', 'Hamburg');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'DE-HH', 'en', 'Hamburg');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'DE-MV', 'de', 'Mecklenburg-Vorpommern');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'DE-MV', 'en', 'Mecklenburg-Western Pomerania');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'DE-NI', 'de', 'Niedersachsen');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'DE-NI', 'en', 'Lower Saxony');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'DE-NW', 'de', 'Nordrhein-Westfalen');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'DE-NW', 'en', 'North Rhine-Westphalia');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'DE-RP', 'de', 'Rheinland-Pfalz');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'DE-RP', 'en', 'Rhineland-Palatinate');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'DE-SH', 'de', 'Schleswig-Holstein');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'DE-SH', 'en', 'Schleswig-Holstein');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'DE-SL', 'de', 'Saarland');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'DE-SL', 'en', 'Saarland');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'DE-SN', 'de', 'Sachsen');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'DE-SN', 'en', 'Saxony');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'DE-ST', 'de', 'Sachsen-Anhalt');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'DE-ST', 'en', 'Saxony-Anhalt');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'DE-TH', 'de', 'Thüringen');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'DE-TH', 'en', 'Thuringia');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-AK', 'de', 'Alaska');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-AK', 'en', 'Alaska');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-AL', 'de', 'Alabama');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-AL', 'en', 'Alabama');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-AR', 'de', 'Arkansas');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-AR', 'en', 'Arkansas');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-AZ', 'de', 'Arizona');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-AZ', 'en', 'Arizona');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-CA', 'de', 'Kalifornien');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-CA', 'en', 'California');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-CO', 'de', 'Colorado');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-CO', 'en', 'Colorado');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-CT', 'de', 'Connecticut');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-CT', 'en', 'Connecticut');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-DC', 'de', 'District of Columbia');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-DC', 'en', 'District of Columbia');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-DE', 'de', 'Delaware');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-DE', 'en', 'Delaware');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-FL', 'de', 'Florida');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-FL', 'en', 'Florida');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-GA', 'de', 'Georgia');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-GA', 'en', 'Georgia');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-HI', 'de', 'Hawaii');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-HI', 'en', 'Hawaii');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-IA', 'de', 'Iowa');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-IA', 'en', 'Iowa');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-ID', 'de', 'Idaho');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-ID', 'en', 'Idaho');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-IL', 'de', 'Illinois');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-IL', 'en', 'Illinois');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-IN', 'de', 'Indiana');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-IN', 'en', 'Indiana');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-KS', 'de', 'Kansas');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-KS', 'en', 'Kansas');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-KY', 'de', 'Kentucky');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-KY', 'en', 'Kentucky');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-LA', 'de', 'Louisiana');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-LA', 'en', 'Louisiana');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-MA', 'de', 'Massachusetts');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-MA', 'en', 'Massachusetts');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-MD', 'de', 'Maryland');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-MD', 'en', 'Maryland');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-ME', 'de', 'Maine');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-ME', 'en', 'Maine');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-MI', 'de', 'Michigan');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-MI', 'en', 'Michigan');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-MN', 'de', 'Minnesota');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-MN', 'en', 'Minnesota');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-MO', 'de', 'Missouri');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-MO', 'en', 'Missouri');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-MS', 'de', 'Mississippi');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-MS', 'en', 'Mississippi');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-MT', 'de', 'Montana');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-MT', 'en', 'Montana');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-NC', 'de', 'North Carolina');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-NC', 'en', 'North Carolina');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-ND', 'de', 'North Dakota');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-ND', 'en', 'North Dakota');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-NE', 'de', 'Nebraska');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-NE', 'en', 'Nebraska');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-NH', 'de', 'New Hampshire');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-NH', 'en', 'New Hampshire');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-NJ', 'de', 'New Jersey');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-NJ', 'en', 'New Jersey');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-NM', 'de', 'New Mexico');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-NM', 'en', 'New Mexico');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-NV', 'de', 'Nevada');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-NV', 'en', 'Nevada');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-NY', 'de', 'New York');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-NY', 'en', 'New York');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-OH', 'de', 'Ohio');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-OH', 'en', 'Ohio');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-OK', 'de', 'Oklahoma');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-OK', 'en', 'Oklahoma');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-OR', 'de', 'Oregon');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-OR', 'en', 'Oregon');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-PA', 'de', 'Pennsylvania');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-PA', 'en', 'Pennsylvania');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-RI', 'de', 'Rhode Island');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-RI', 'en', 'Rhode Island');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-SC', 'de', 'South Carolina');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-SC', 'en', 'South Carolina');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-SD', 'de', 'South Dakota');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-SD', 'en', 'South Dakota');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-TN', 'de', 'Tennessee');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-TN', 'en', 'Tennessee');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-TX', 'de', 'Texas');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-TX', 'en', 'Texas');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-UT', 'de', 'Utah');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-UT', 'en', 'Utah');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-VA', 'de', 'Virginia');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-VA', 'en', 'Virginia');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-VT', 'de', 'Vermont');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-VT', 'en', 'Vermont');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-WA', 'de', 'Washington');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-WA', 'en', 'Washington');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-WI', 'de', 'Wisconsin');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-WI', 'en', 'Wisconsin');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-WV', 'de', 'West Virginia');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-WV', 'en', 'West Virginia');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-WY', 'de', 'Wyoming');
INSERT INTO "OLINGO"."AdministrativeDivisionDescription" VALUES ('ISO', '3166-2', 'US-WY', 'en', 'Wyoming');

--------ADMINISTRATIVE DIVISION--------------------------------------------------------------------------------------------------
CREATE TABLE "OLINGO"."AdministrativeDivision"
(
    "CodePublisher"      VARCHAR(10) NOT NULL,
    "CodeID"             VARCHAR(10) NOT NULL,
    "DivisionCode"       VARCHAR(10) NOT NULL,
    "CountryISOCode"     VARCHAR(4)  NOT NULL,
    "ParentCodeID"       VARCHAR(10),
    "ParentDivisionCode" VARCHAR(10),
    "AlternativeCode"    VARCHAR(10),
    "Area"               INTEGER, --DECIMAL(34,0),
    "Population"         BIGINT,
    PRIMARY KEY ("CodePublisher", "CodeID", "DivisionCode")
);

--Eurostat
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'LAU2', '31003', 'BEL', 'NUTS3', 'BE251', null, 71675809, 15493);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'LAU2', '31004', 'BEL', 'NUTS3', 'BE251', null, 17411180, 20028);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'LAU2', '31005', 'BEL', 'NUTS3', 'BE251', null, 138402202, 118335);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'LAU2', '31006', 'BEL', 'NUTS3', 'BE251', null, 89520475, 10885);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'LAU2', '31012', 'BEL', 'NUTS3', 'BE251', null, 53764838, 13861);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'LAU2', '31022', 'BEL', 'NUTS3', 'BE251', null, 79645460, 23133);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'LAU2', '31033', 'BEL', 'NUTS3', 'BE251', null, 45232765, 20371);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'LAU2', '31040', 'BEL', 'NUTS3', 'BE251', null, 60335913, 22424);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'LAU2', '31042', 'BEL', 'NUTS3', 'BE251', null, 48862499, 2720);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'LAU2', '31043', 'BEL', 'NUTS3', 'BE251', null, 56443228, 33485);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'LAU2', '32003', 'BEL', 'NUTS3', 'BE252', null, 149401818, 16564);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'LAU2', '32006', 'BEL', 'NUTS3', 'BE252', null, 55893936, 9995);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'LAU2', '32010', 'BEL', 'NUTS3', 'BE252', null, 39185258, 8712);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'LAU2', '32011', 'BEL', 'NUTS3', 'BE252', null, 54999796, 12357);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'LAU2', '32030', 'BEL', 'NUTS3', 'BE252', null, 62938759, 3282);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'LAU2', '33011', 'BEL', 'NUTS3', 'BE253', null, 130610415, 35098);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'LAU2', '33016', 'BEL', 'NUTS3', 'BE253', null, 3578335, 1037);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'LAU2', '33021', 'BEL', 'NUTS3', 'BE253', null, 119330610, 19968);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'LAU2', '33029', 'BEL', 'NUTS3', 'BE253', null, 43612479, 18456);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'LAU2', '33037', 'BEL', 'NUTS3', 'BE253', null, 67573324, 12400);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'LAU2', '33039', 'BEL', 'NUTS3', 'BE253', null, 94235304, 7888);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'LAU2', '33040', 'BEL', 'NUTS3', 'BE253', null, 52529046, 8144);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'LAU2', '33041', 'BEL', 'NUTS3', 'BE253', null, 38148241, 3625);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'LAU2', '34002', 'BEL', 'NUTS3', 'BE254', null, 41788652, 14580);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'LAU2', '34003', 'BEL', 'NUTS3', 'BE254', null, 21748127, 9803);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'LAU2', '34009', 'BEL', 'NUTS3', 'BE254', null, 16815679, 11687);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'LAU2', '34013', 'BEL', 'NUTS3', 'BE254', null, 29140007, 27476);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'LAU2', '34022', 'BEL', 'NUTS3', 'BE254', null, 80020386, 75577);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'LAU2', '34023', 'BEL', 'NUTS3', 'BE254', null, 10008346, 13113);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'LAU2', '34025', 'BEL', 'NUTS3', 'BE254', null, 13150180, 5756);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'LAU2', '34027', 'BEL', 'NUTS3', 'BE254', null, 33070836, 33099);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'LAU2', '34040', 'BEL', 'NUTS3', 'BE254', null, 44343752, 37385);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'LAU2', '34041', 'BEL', 'NUTS3', 'BE254', null, 38761463, 31345);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'LAU2', '34042', 'BEL', 'NUTS3', 'BE254', null, 63242438, 24301);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'LAU2', '34043', 'BEL', 'NUTS3', 'BE254', null, 10776650, 2155);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'LAU2', '35002', 'BEL', 'NUTS3', 'BE255', null, 13079087, 17333);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'LAU2', '35005', 'BEL', 'NUTS3', 'BE255', null, 42254065, 11771);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'LAU2', '35006', 'BEL', 'NUTS3', 'BE255', null, 45334695, 13972);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'LAU2', '35011', 'BEL', 'NUTS3', 'BE255', null, 75653353, 19312);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'LAU2', '35013', 'BEL', 'NUTS3', 'BE255', null, 37723883, 70813);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'LAU2', '35014', 'BEL', 'NUTS3', 'BE255', null, 35383003, 9231);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'LAU2', '35029', 'BEL', 'NUTS3', 'BE255', null, 42169175, 12611);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'LAU2', '36006', 'BEL', 'NUTS3', 'BE256', null, 37836247, 10079);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'LAU2', '36007', 'BEL', 'NUTS3', 'BE256', null, 16157265, 10748);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'LAU2', '36008', 'BEL', 'NUTS3', 'BE256', null, 25483137, 27449);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'LAU2', '36010', 'BEL', 'NUTS3', 'BE256', null, 24758200, 9519);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'LAU2', '36011', 'BEL', 'NUTS3', 'BE256', null, 25931415, 8625);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'LAU2', '36012', 'BEL', 'NUTS3', 'BE256', null, 35341058, 10964);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'LAU2', '36015', 'BEL', 'NUTS3', 'BE256', null, 59793935, 60707);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'LAU2', '36019', 'BEL', 'NUTS3', 'BE256', null, 46240034, 11196);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'LAU2', '37002', 'BEL', 'NUTS3', 'BE257', null, 25935511, 8376);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'LAU2', '37007', 'BEL', 'NUTS3', 'BE257', null, 29348057, 11039);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'LAU2', '37010', 'BEL', 'NUTS3', 'BE257', null, 16621841, 7715);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'LAU2', '37011', 'BEL', 'NUTS3', 'BE257', null, 34421545, 6798);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'LAU2', '37012', 'BEL', 'NUTS3', 'BE257', null, 30201523, 5266);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'LAU2', '37015', 'BEL', 'NUTS3', 'BE257', null, 68504357, 20110);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'LAU2', '37017', 'BEL', 'NUTS3', 'BE257', null, 21760376, 9441);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'LAU2', '37018', 'BEL', 'NUTS3', 'BE257', null, 68420735, 14283);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'LAU2', '37020', 'BEL', 'NUTS3', 'BE257', null, 34576094, 9072);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'LAU2', '38002', 'BEL', 'NUTS3', 'BE258', null, 80009790, 5007);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'LAU2', '38008', 'BEL', 'NUTS3', 'BE258', null, 23896896, 10854);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'LAU2', '38014', 'BEL', 'NUTS3', 'BE258', null, 43959624, 22202);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'LAU2', '38016', 'BEL', 'NUTS3', 'BE258', null, 31004430, 11434);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'LAU2', '38025', 'BEL', 'NUTS3', 'BE258', null, 96339703, 11509);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'NUTS1', 'BE1', 'BEL', null, null, null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'NUTS1', 'BE2', 'BEL', null, null, null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'NUTS1', 'BE3', 'BEL', null, null, null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'NUTS2', 'BE10', 'BEL', 'NUTS1', 'BE1', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'NUTS2', 'BE21', 'BEL', 'NUTS1', 'BE2', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'NUTS2', 'BE22', 'BEL', 'NUTS1', 'BE2', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'NUTS2', 'BE23', 'BEL', 'NUTS1', 'BE2', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'NUTS2', 'BE24', 'BEL', 'NUTS1', 'BE2', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'NUTS2', 'BE25', 'BEL', 'NUTS1', 'BE2', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'NUTS2', 'BE31', 'BEL', 'NUTS1', 'BE3', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'NUTS2', 'BE32', 'BEL', 'NUTS1', 'BE3', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'NUTS2', 'BE33', 'BEL', 'NUTS1', 'BE3', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'NUTS2', 'BE34', 'BEL', 'NUTS1', 'BE3', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'NUTS2', 'BE35', 'BEL', 'NUTS1', 'BE3', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'NUTS3', 'BE100', 'BEL', 'NUTS2', 'BE10', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'NUTS3', 'BE211', 'BEL', 'NUTS2', 'BE21', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'NUTS3', 'BE212', 'BEL', 'NUTS2', 'BE21', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'NUTS3', 'BE213', 'BEL', 'NUTS2', 'BE21', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'NUTS3', 'BE221', 'BEL', 'NUTS2', 'BE22', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'NUTS3', 'BE222', 'BEL', 'NUTS2', 'BE22', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'NUTS3', 'BE223', 'BEL', 'NUTS2', 'BE22', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'NUTS3', 'BE231', 'BEL', 'NUTS2', 'BE23', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'NUTS3', 'BE232', 'BEL', 'NUTS2', 'BE23', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'NUTS3', 'BE233', 'BEL', 'NUTS2', 'BE23', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'NUTS3', 'BE234', 'BEL', 'NUTS2', 'BE23', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'NUTS3', 'BE235', 'BEL', 'NUTS2', 'BE23', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'NUTS3', 'BE236', 'BEL', 'NUTS2', 'BE23', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'NUTS3', 'BE241', 'BEL', 'NUTS2', 'BE24', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'NUTS3', 'BE242', 'BEL', 'NUTS2', 'BE24', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'NUTS3', 'BE251', 'BEL', 'NUTS2', 'BE25', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'NUTS3', 'BE252', 'BEL', 'NUTS2', 'BE25', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'NUTS3', 'BE253', 'BEL', 'NUTS2', 'BE25', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'NUTS3', 'BE254', 'BEL', 'NUTS2', 'BE25', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'NUTS3', 'BE255', 'BEL', 'NUTS2', 'BE25', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'NUTS3', 'BE256', 'BEL', 'NUTS2', 'BE25', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'NUTS3', 'BE257', 'BEL', 'NUTS2', 'BE25', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'NUTS3', 'BE258', 'BEL', 'NUTS2', 'BE25', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'NUTS3', 'BE310', 'BEL', 'NUTS2', 'BE31', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'NUTS3', 'BE321', 'BEL', 'NUTS2', 'BE32', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'NUTS3', 'BE322', 'BEL', 'NUTS2', 'BE32', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'NUTS3', 'BE323', 'BEL', 'NUTS2', 'BE32', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'NUTS3', 'BE324', 'BEL', 'NUTS2', 'BE32', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'NUTS3', 'BE325', 'BEL', 'NUTS2', 'BE32', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'NUTS3', 'BE326', 'BEL', 'NUTS2', 'BE32', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'NUTS3', 'BE327', 'BEL', 'NUTS2', 'BE32', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'NUTS3', 'BE331', 'BEL', 'NUTS2', 'BE33', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'NUTS3', 'BE332', 'BEL', 'NUTS2', 'BE33', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'NUTS3', 'BE334', 'BEL', 'NUTS2', 'BE33', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'NUTS3', 'BE335', 'BEL', 'NUTS2', 'BE33', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'NUTS3', 'BE336', 'BEL', 'NUTS2', 'BE33', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'NUTS3', 'BE341', 'BEL', 'NUTS2', 'BE34', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'NUTS3', 'BE342', 'BEL', 'NUTS2', 'BE34', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'NUTS3', 'BE343', 'BEL', 'NUTS2', 'BE34', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'NUTS3', 'BE344', 'BEL', 'NUTS2', 'BE34', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'NUTS3', 'BE345', 'BEL', 'NUTS2', 'BE34', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'NUTS3', 'BE351', 'BEL', 'NUTS2', 'BE35', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'NUTS3', 'BE352', 'BEL', 'NUTS2', 'BE35', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('Eurostat', 'NUTS3', 'BE353', 'BEL', 'NUTS2', 'BE35', null, 0, 0);

INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-1', 'BEL', 'BEL', null, null, null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-1', 'DEU', 'DEU', null, null, null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-1', 'USA', 'USA', null, null, null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'BE-BRU', 'BEL', '3166-1', 'BEL', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'BE-VAN', 'BEL', '3166-2', 'BE-VLG', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'BE-VBR', 'BEL', '3166-2', 'BE-VLG', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'BE-VLG', 'BEL', '3166-1', 'BEL', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'BE-VLI', 'BEL', '3166-2', 'BE-VLG', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'BE-VOV', 'BEL', '3166-2', 'BE-VLG', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'BE-VWV', 'BEL', '3166-2', 'BE-VLG', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'BE-WAL', 'BEL', '3166-1', 'BEL', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'BE-WBR', 'BEL', '3166-2', 'BE-WAL', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'BE-WHT', 'BEL', '3166-2', 'BE-WAL', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'BE-WLG', 'BEL', '3166-2', 'BE-WAL', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'BE-WLX', 'BEL', '3166-2', 'BE-WAL', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'BE-WNA', 'BEL', '3166-2', 'BE-WAL', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'DE-BB', 'DEU', '3166-1', 'DEU', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'DE-BE', 'DEU', '3166-1', 'DEU', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'DE-BW', 'DEU', '3166-1', 'DEU', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'DE-BY', 'DEU', '3166-1', 'DEU', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'DE-HB', 'DEU', '3166-1', 'DEU', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'DE-HE', 'DEU', '3166-1', 'DEU', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'DE-HH', 'DEU', '3166-1', 'DEU', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'DE-MV', 'DEU', '3166-1', 'DEU', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'DE-NI', 'DEU', '3166-1', 'DEU', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'DE-NW', 'DEU', '3166-1', 'DEU', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'DE-RP', 'DEU', '3166-1', 'DEU', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'DE-SH', 'DEU', '3166-1', 'DEU', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'DE-SL', 'DEU', '3166-1', 'DEU', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'DE-SN', 'DEU', '3166-1', 'DEU', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'DE-ST', 'DEU', '3166-1', 'DEU', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'DE-TH', 'DEU', '3166-1', 'DEU', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'US-AK', 'USA', '3166-1', 'USA', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'US-AL', 'USA', '3166-1', 'USA', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'US-AR', 'USA', '3166-1', 'USA', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'US-AZ', 'USA', '3166-1', 'USA', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'US-CA', 'USA', '3166-1', 'USA', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'US-CO', 'USA', '3166-1', 'USA', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'US-CT', 'USA', '3166-1', 'USA', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'US-DC', 'USA', '3166-1', 'USA', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'US-DE', 'USA', '3166-1', 'USA', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'US-FL', 'USA', '3166-1', 'USA', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'US-GA', 'USA', '3166-1', 'USA', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'US-HI', 'USA', '3166-1', 'USA', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'US-IA', 'USA', '3166-1', 'USA', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'US-ID', 'USA', '3166-1', 'USA', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'US-IL', 'USA', '3166-1', 'USA', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'US-IN', 'USA', '3166-1', 'USA', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'US-KS', 'USA', '3166-1', 'USA', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'US-KY', 'USA', '3166-1', 'USA', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'US-LA', 'USA', '3166-1', 'USA', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'US-MA', 'USA', '3166-1', 'USA', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'US-MD', 'USA', '3166-1', 'USA', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'US-ME', 'USA', '3166-1', 'USA', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'US-MI', 'USA', '3166-1', 'USA', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'US-MN', 'USA', '3166-1', 'USA', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'US-MO', 'USA', '3166-1', 'USA', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'US-MS', 'USA', '3166-1', 'USA', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'US-MT', 'USA', '3166-1', 'USA', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'US-NC', 'USA', '3166-1', 'USA', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'US-ND', 'USA', '3166-1', 'USA', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'US-NE', 'USA', '3166-1', 'USA', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'US-NH', 'USA', '3166-1', 'USA', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'US-NJ', 'USA', '3166-1', 'USA', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'US-NM', 'USA', '3166-1', 'USA', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'US-NV', 'USA', '3166-1', 'USA', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'US-NY', 'USA', '3166-1', 'USA', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'US-OH', 'USA', '3166-1', 'USA', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'US-OK', 'USA', '3166-1', 'USA', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'US-OR', 'USA', '3166-1', 'USA', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'US-PA', 'USA', '3166-1', 'USA', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'US-RI', 'USA', '3166-1', 'USA', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'US-SC', 'USA', '3166-1', 'USA', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'US-SD', 'USA', '3166-1', 'USA', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'US-TN', 'USA', '3166-1', 'USA', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'US-TX', 'USA', '3166-1', 'USA', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'US-UT', 'USA', '3166-1', 'USA', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'US-VA', 'USA', '3166-1', 'USA', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'US-VT', 'USA', '3166-1', 'USA', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'US-WA', 'USA', '3166-1', 'USA', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'US-WI', 'USA', '3166-1', 'USA', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'US-WV', 'USA', '3166-1', 'USA', null, 0, 0);
INSERT INTO "OLINGO"."AdministrativeDivision" VALUES ('ISO', '3166-2', 'US-WY', 'USA', '3166-1', 'USA', null, 0, 0);

CREATE TABLE "OLINGO"."Comment" (
    "BusinessPartnerID" VARCHAR(32) NOT NULL,
    "Order"             INTEGER     NOT NULL,
    "Text"              VARCHAR(280),
    PRIMARY KEY ("BusinessPartnerID", "Order"));

INSERT INTO "OLINGO"."Comment" VALUES ('1', 1, 'This is just a test');
INSERT INTO "OLINGO"."Comment" VALUES ('1', 3, 'This is another test');

--------DUMMY FOR TESTING--------------------------------------------------------------------------------------------------------
CREATE TABLE "OLINGO"."DummyToBeIgnored"
(
    "ID" VARCHAR(32) NOT NULL,
    PRIMARY KEY ("ID")
);

--------User defined scalar functions--------------------------------------------------------------------------------------------
CREATE FUNCTION OLINGO."PopulationDensity"("Area" BIGINT, "Population" BIGINT)
    RETURNS DOUBLE
BEGIN ATOMIC
    IF "Area" <= 0 THEN RETURN 0;
    ELSE RETURN CAST("Population" / "Area" AS DOUBLE);
    END IF;
END;

CREATE FUNCTION OLINGO."ConvertToQkm"("Area" BIGINT)
    RETURNS BIGINT
BEGIN ATOMIC
    IF "Area" <= 0 THEN RETURN 0;
    ELSE RETURN "Area" / 1000000;
    END IF;
END;

CREATE FUNCTION "Siblings" ("Publisher" VARCHAR(10), "ID" VARCHAR(10), "Division" VARCHAR(10))
	RETURNS TABLE(
		"CodePublisher" VARCHAR(10),
		"CodeID" VARCHAR(10),
		"DivisionCode" VARCHAR(10),
		"CountryISOCode" VARCHAR(4),
		"ParentCodeID" VARCHAR(10),
		"ParentDivisionCode" VARCHAR(10),
		"AlternativeCode" VARCHAR(10),
		"Area" int,
		"Population" BIGINT)
	READS SQL DATA
	RETURN TABLE( SELECT *
FROM "AdministrativeDivision" as a
WHERE EXISTS (SELECT "CodePublisher"
				FROM "AdministrativeDivision" as b
				WHERE b."CodeID" = "ID"
				AND b."DivisionCode" = "Division"
				AND b."CodePublisher" = a."CodePublisher"
				AND b."ParentCodeID" = a."ParentCodeID"
				AND b."ParentDivisionCode" = a."ParentDivisionCode")
				AND NOT( a."CodePublisher" = "Publisher"
					    AND a."CodeID" = "ID"
					    AND a."DivisionCode" = "Division" )
				);