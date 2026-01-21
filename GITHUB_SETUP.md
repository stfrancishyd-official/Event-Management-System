# 🚀 GitHub Setup Guide

## 📋 Pre-Push Checklist

✅ **Security Check:**
- `application.properties` is ignored by Git (contains sensitive data)
- `application.properties.example` is included (template for others)
- `.gitignore` files are properly configured
- No sensitive credentials in any committed files

✅ **Project Structure:**
- Backend (Spring Boot) ✓
- Frontend (React) ✓
- Documentation (README.md) ✓
- Setup guides ✓

## 🔧 Step-by-Step GitHub Setup

### 1. Create GitHub Repository

1. **Go to GitHub.com** and login
2. **Click "New Repository"** (green button)
3. **Repository Details:**
   - Repository name: `event-registration-system`
   - Description: `Event Registration Management System for Educational Institutions`
   - Visibility: `Public` (or Private if you prefer)
   - ✅ Add a README file: **UNCHECK** (we already have one)
   - ✅ Add .gitignore: **UNCHECK** (we already have one)
   - ✅ Choose a license: **MIT License** (recommended)

4. **Click "Create repository"**

### 2. Initialize Local Git Repository

Open terminal/command prompt in your project root directory:

```bash
# Initialize Git repository
git init

# Add all files to staging
git add .

# Check what will be committed (make sure no sensitive files)
git status

# Commit initial version
git commit -m "Initial commit: Event Registration Management System

- Complete Spring Boot backend with JWT authentication
- React frontend with role-based access
- MySQL database integration
- Email verification system
- Student, Faculty, and Admin dashboards
- Comprehensive documentation and setup guides"
```

### 3. Connect to GitHub Repository

Replace `yourusername` with your actual GitHub username:

```bash
# Add remote repository
git remote add origin https://github.com/yourusername/event-registration-system.git

# Set main branch
git branch -M main

# Push to GitHub
git push -u origin main
```

### 4. Verify Upload

1. **Go to your GitHub repository**
2. **Check that files are uploaded:**
   - ✅ Backend/ folder with all Java files
   - ✅ Frontend/ folder with all React files
   - ✅ README.md with project documentation
   - ✅ .gitignore files
   - ✅ setup.md and other guides
   - ❌ **application.properties should NOT be visible** (security)

## 🔒 Security Verification

**IMPORTANT:** Make sure these files are **NOT** visible on GitHub:
- `Backend/src/main/resources/application.properties`
- Any files with real passwords or API keys
- `node_modules/` folder
- `target/` folder

**These files SHOULD be visible:**
- `Backend/src/main/resources/application.properties.example`
- All source code files (.java, .js, .jsx)
- Documentation files (.md)
- Configuration files (pom.xml, package.json)

## 🎯 After Successful Upload

### For Collaborators/Users:

1. **Clone the repository:**
   ```bash
   git clone https://github.com/yourusername/event-registration-system.git
   cd event-registration-system
   ```

2. **Setup configuration:**
   ```bash
   # Copy template to actual config file
   cp Backend/src/main/resources/application.properties.example Backend/src/main/resources/application.properties
   
   # Edit with actual credentials
   # Update database, email, and JWT settings
   ```

3. **Follow setup instructions** in README.md

### Repository Settings (Optional):

1. **Go to repository Settings**
2. **Add topics/tags:** `spring-boot`, `react`, `mysql`, `jwt`, `event-management`
3. **Enable Issues** for bug reports
4. **Enable Discussions** for community questions
5. **Set up branch protection** for main branch (if working with team)

## 🔄 Future Updates

When you make changes to the project:

```bash
# Add changes
git add .

# Commit with descriptive message
git commit -m "Add feature: Event poster upload functionality"

# Push to GitHub
git push origin main
```

## 🆘 Troubleshooting

### If you see sensitive data on GitHub:
1. **Immediately remove the repository** (if just created)
2. **Fix .gitignore** and **remove sensitive files**
3. **Create new repository** and push again

### If push is rejected:
```bash
# Pull latest changes first
git pull origin main --allow-unrelated-histories

# Then push
git push origin main
```

### If you need to remove a file from Git history:
```bash
# Remove file from Git but keep locally
git rm --cached Backend/src/main/resources/application.properties

# Commit the removal
git commit -m "Remove sensitive application.properties from Git"

# Push changes
git push origin main
```

## 🎉 Success!

Your Event Registration Management System is now on GitHub! 

**Next Steps:**
1. Share the repository link with collaborators
2. Add a nice project description and tags
3. Consider adding a license file
4. Set up GitHub Actions for CI/CD (advanced)

**Repository URL will be:**
`https://github.com/yourusername/event-registration-system`