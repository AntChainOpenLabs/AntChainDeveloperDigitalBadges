INSERT INTO `xbuilders_chain_config` (`chain_id`,`chain_name`,`access_key`,`access_secret`,`admin_account_name`,`admin_account_kms_id`,`del_status`,`gmt_create`,`gmt_modified`,`tenant`,`chain_browser_url`)
VALUES ('0b06e2744ad341538b7a3b3cc5ad9e57','开发者实验链','',
        '',
        '','',1,'2022-09-23 16:44:02','2023-04-24 18:51:09','','https://chaininsight.antdigital.com/home?unionId=101697');
INSERT INTO `xbuilders_chain_config` (`chain_id`,`chain_name`,`access_key`,`access_secret`,`admin_account_name`,`admin_account_kms_id`,`del_status`,`gmt_create`,`gmt_modified`,`tenant`,`chain_browser_url`)
VALUES ('a00e36c5','开放联盟链','',
        '',
        '','',1,'2022-10-31 13:58:41','2023-04-24 18:51:12','','https://chaininsight.antdigital.com/home?unionId=2647');
INSERT INTO lab_dev_community_asset (id, group_id, chain_id, contract_id, contract_name, contract_type, sub_id,
                                     asset_type, name, redeemed_amount, total_amount)
VALUES ('44e3053a-8790-4ad3-83ae-0229fcc6cd7b', NULL, 'a00e36c5',
        '703761a7279ea175485308b9a5ad872be916b89db8bd83673f10d463051ea3d6',
        'XBuildersNFT_1684807190107', 0, NULL, 0, '测试头像', 0, 100);
INSERT INTO lab_dev_community_asset (id, group_id, chain_id, contract_id, contract_name, contract_type, sub_id,
                                     asset_type, name, redeemed_amount, total_amount)
VALUES ('54e3053a-8790-4ad3-83ae-0229fcc6cd7b', NULL, 'a00e36c5',
        '803761a7279ea175485308b9a5ad872be916b89db8bd83673f10d463051ea3d6',
        'XBuildersNFTURI_1684929795221', 0, NULL, 0, '测试头像', 0, 100);