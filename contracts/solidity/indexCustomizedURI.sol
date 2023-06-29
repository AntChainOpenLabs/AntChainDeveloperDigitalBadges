pragma solidity ^0.8.0;

import "./@openzeppelin/contracts/token/ERC721/ERC721.sol";
import "./@openzeppelin/contracts/utils/Counters.sol";
import "./@openzeppelin/contracts/access/AccessControl.sol";

contract XBuildersNFTURI is ERC721, AccessControl {
    using Counters for Counters.Counter;

    Counters.Counter private _tokenIdCounter;

    // Optional mapping for token URIs
    mapping(uint256 => string) private _tokenURIs;

    // /**
    //  * @dev Initializes the contract by setting a `name` and a `symbol` to the token collection.
    //  * @param name The name of the token collection, e.g "X-Builders Avatar".
    //  * @param symbol The symbol of the token collection, e.g "XBA".
    //  */
    constructor() public ERC721("X-Builders Avatar", "XBA") {
        _grantRole(DEFAULT_ADMIN_ROLE, msg.sender);
    }

    function safeMint(
        identity to,
        string memory uri
    ) public onlyRole(DEFAULT_ADMIN_ROLE) {
        uint256 tokenId = _tokenIdCounter.current();
        _tokenIdCounter.increment();
        _safeMint(to, tokenId);
        _setTokenURI(tokenId, uri);
    }

    function supportsInterface(
        bytes4 interfaceId
    ) public view override(ERC721, AccessControl) returns (bool) {
        return super.supportsInterface(interfaceId);
    }

    function tokenURI(
        uint256 tokenId
    ) public view virtual override returns (string memory) {
        require(
            _exists(tokenId),
            "ERC721URIStorage: URI query for nonexistent token"
        );

        string memory _tokenURI = _tokenURIs[tokenId];
        string memory base = _baseURI();

        // If there is no base URI, return the token URI.
        if (bytes(base).length == 0) {
            return _tokenURI;
        }
        // If both are set, concatenate the baseURI and tokenURI (via abi.encodePacked).
        if (bytes(_tokenURI).length > 0) {
            return string(abi.encodePacked(base, _tokenURI));
        }

        return super.tokenURI(tokenId);
    }

    function _setTokenURI(
        uint256 tokenId,
        string memory _tokenURI
    ) internal virtual {
        require(
            _exists(tokenId),
            "ERC721URIStorage: URI set of nonexistent token"
        );
        _tokenURIs[tokenId] = _tokenURI;
    }
}
