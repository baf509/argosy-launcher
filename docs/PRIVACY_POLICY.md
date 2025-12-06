# Privacy Policy for Argosy Launcher

**Last Updated:** December 2024

## Overview

Argosy Launcher ("the App") is an open-source Android launcher application. This privacy policy explains how the App handles user data.

## Data Collection

### Data We Do NOT Collect
- Personal identification information
- Location data
- Analytics or usage statistics
- Advertising identifiers
- Contact information

### Data Stored Locally
The following data is stored **only on your device** and is never transmitted externally:

- **App List**: List of installed applications for display in the launcher
- **User Preferences**: Layout settings, theme preferences, and configuration options
- **RomM Server Configuration**: Connection details for your self-hosted RomM server (if configured)
- **Game Library Cache**: Cached game metadata from your RomM server

## Network Communications

The App connects to the internet **only** when you configure a RomM server connection. In this case:

- The App communicates exclusively with **your self-hosted RomM server**
- No data is sent to the developers or any third-party services
- Connection credentials are stored locally on your device

## Permissions

### QUERY_ALL_PACKAGES
Required to display installed applications in the launcher. This information never leaves your device.

### INTERNET & ACCESS_NETWORK_STATE
Used solely for connecting to your self-hosted RomM server.

### READ/WRITE_EXTERNAL_STORAGE
Used to access game files stored on your device for the RomM integration.

## Data Security

All data is stored locally using Android's standard security mechanisms. Server credentials are stored in encrypted SharedPreferences.

## Third-Party Services

The App does not integrate with any third-party analytics, advertising, or tracking services.

## Children's Privacy

The App does not knowingly collect data from children under 13.

## Changes to This Policy

Updates to this privacy policy will be reflected in the "Last Updated" date above.

## Contact

For privacy concerns, please open an issue at the project's GitHub repository.

## Open Source

Argosy Launcher is open-source software. You can review the complete source code to verify these privacy practices.
