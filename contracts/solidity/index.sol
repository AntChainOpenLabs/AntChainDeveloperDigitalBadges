pragma solidity ^0.8.0;

import "./@openzeppelin/contracts/token/ERC721/ERC721.sol";
import "./@openzeppelin/contracts/access/AccessControl.sol";
import "./@openzeppelin/contracts/utils/Counters.sol";

contract XBuildersNFT is ERC721, AccessControl {
    using Counters for Counters.Counter;
    Counters.Counter private _tokenIdCounter;

    // /**
    //  * @dev Initializes the contract by setting a `name` and a `symbol` to the token collection.
    //  * @param name The name of the token collection, e.g "X-Builders Avatar".
    //  * @param symbol The symbol of the token collection, e.g "XBA".
    //  */
    constructor() ERC721("X-Builders Avatar", "XBA") {
        _grantRole(DEFAULT_ADMIN_ROLE, msg.sender);
    }

    function safeMint(identity to) public onlyRole(DEFAULT_ADMIN_ROLE) {
        uint256 tokenId = _tokenIdCounter.current();
        _tokenIdCounter.increment();
        _safeMint(to, tokenId);
    }

    /**
     * @dev Replaces the return value to set a `baseURI` to the token collection.
     */
    function _baseURI() internal view override returns (string memory) {
        return
            "https://openlab-api.antchain.antgroup.com/api/developerLab/community/getAssetUri/0d3da6b3-f864-422d-8123-eae3c20317d6/";
    }

    function supportsInterface(
        bytes4 interfaceId
    ) public view override(ERC721, AccessControl) returns (bool) {
        return super.supportsInterface(interfaceId);
    }
}
